/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.io.path

import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.attribute.BasicFileAttributes

internal class SecurePathTreeWalk private constructor(
    private val linkOptions: Array<LinkOption>,
    private var onFile: ((Path) -> Unit)?,
    private var onEnter: ((Path) -> Unit)?,
    private var onLeave: ((Path) -> Unit)?,
    private var onFail: ((f: Path, e: Exception) -> Unit)?,
) {
    constructor(followLinks: Boolean) : this(
        linkOptions = LinkFollowing.toOptions(followLinks),
        onFile = null,
        onEnter = null,
        onLeave = null,
        onFail = null
    )

    fun onFile(function: (Path) -> Unit): SecurePathTreeWalk {
        return this.apply { onFile = function }
    }

    fun onEnterDirectory(function: (Path) -> Unit): SecurePathTreeWalk {
        return this.apply { onEnter = function }
    }

    fun onLeaveDirectory(function: (Path) -> Unit): SecurePathTreeWalk {
        return this.apply { onLeave = function }
    }

    fun onFail(function: (Path, Exception) -> Unit): SecurePathTreeWalk {
        return this.apply { onFail = function }
    }

    private fun tryInvoke(function: ((Path) -> Unit)?, path: Path) {
        try {
            function?.invoke(path)
        } catch (exception: Exception) {
            onFail?.invoke(path, exception) ?: throw exception
        }
    }

    private var stack: PathNode? = null

    private fun beforeWalkingEntries(path: Path, key: Any?) {
        stack = PathNode(path, key, stack)
        if (stack!!.createsCycle())
            throw FileSystemLoopException(path.toString())

        tryInvoke(onEnter, path)
    }

    private fun afterWalkingEntries(path: Path) {
        tryInvoke(onLeave, path)
        stack = stack!!.parent
    }

    // TODO: Guava opens parent directory stream to make sure all checks are done in a secure environment.
    fun walk(path: Path): Unit {
        if (!path.isDirectory(*linkOptions)) {
            if (path.exists(LinkOption.NOFOLLOW_LINKS)) {
                tryInvoke(onFile, path)
            }
            return
        }

        val key = path.readAttributes<BasicFileAttributes>(*linkOptions).fileKey()
        beforeWalkingEntries(path, key)

        try {
            // behavior not documented for symlinks
            Files.newDirectoryStream(path).use { directoryStream ->
                if (directoryStream is SecureDirectoryStream) {
                    directoryStream.walkEntries()
                } else {
                    directoryStream.insecureWalkEntries()
                }
            }
        } catch (exception: Exception) {
            onFail?.invoke(path, exception)
        }

        afterWalkingEntries(path)

        /* catch (_: NotDirectoryException) {
            if (start.exists(LinkOption.NOFOLLOW_LINKS)) {
                onFile?.invoke(start)
            }
        }*/
    }

    // secure walk

    private fun SecureDirectoryStream<Path>.walkEntries() {
        for (entry in this) {
            val attributes = directoryAttributesOrNull(entry)

            if (attributes != null) {
                enterDirectory(entry, attributes.fileKey())
            } else {
                tryInvoke(onFile, entry)
            }
        }
    }

    private fun SecureDirectoryStream<Path>.enterDirectory(path: Path, key: Any?) {
        beforeWalkingEntries(path, key)

        try {
            this.newDirectoryStream(path).use { it.walkEntries() }
        } catch (exception: Exception) {
            onFail?.invoke(path, exception)
        }

        afterWalkingEntries(path)
    }

    /** If the given [path] is a directory, returns its attributes. Returns `null` otherwise. */
    private fun SecureDirectoryStream<Path>.directoryAttributesOrNull(path: Path): BasicFileAttributes? {
        return try {
            getFileAttributeView(path, BasicFileAttributeView::class.java, *linkOptions).readAttributes().takeIf { it.isDirectory }
        } catch (exception: Exception) {
            // ignore
            null
        }
    }

    // insecure walk

    private fun DirectoryStream<Path>.insecureWalkEntries() {
        for (entry in this) {
            val attributes = insecureDirectoryAttributesOrNull(entry)

            if (attributes != null) {
                insecureEnterDirectory(entry, attributes.fileKey())
            } else {
                tryInvoke(onFile, entry)
            }
        }
    }

    private fun insecureEnterDirectory(path: Path, key: Any?) {
        beforeWalkingEntries(path, key)

        try {
            Files.newDirectoryStream(path).use { it.insecureWalkEntries() }
        } catch (exception: Exception) {
            onFail?.invoke(path, exception)
        }

        afterWalkingEntries(path)
    }

    /** If the given [path] is a directory, returns its attributes. Returns `null` otherwise. */
    private fun insecureDirectoryAttributesOrNull(path: Path): BasicFileAttributes? {
        return try {
            path.readAttributes<BasicFileAttributes>(*linkOptions).takeIf { it.isDirectory }
        } catch (exception: Exception) {
            // ignore
            null
        }
    }
}
