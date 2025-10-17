package tools.kotlin.aicligui.pty

import kotlin.time.Duration

/** Execution status of an AI CLI-backed PTY. */
enum class AiCliExecutionStatus { BUSY, IDLE }

/**
 * Result of terminal rendering that includes plain text and cursor metadata.
 *
 * - `cursorX` is 0-based column index (0..columns-1)
 * - `cursorY` is 1-based row index (1..rows)
 */
data class RenderedScreen(
    val text: String,
    val cursorX: Int,
    val cursorY: Int,
    val cursorVisible: Boolean,
)

/** Generalized PTY interface for AI CLI backends. */
interface AiCliPty : AutoCloseable {
    /** Start the PTY-backed process with the desired terminal size. */
    fun start(columns: Int = 120, rows: Int = 64)

    fun isAlive(): Boolean
    fun renderScreen(columns: Int, rows: Int): String
    fun renderScreenWithCursor(columns: Int, rows: Int): RenderedScreen
    fun resize(columns: Int, rows: Int)

    fun sendString(s: String)
    fun sendBytes(bytes: ByteArray)
    fun sendEnter()
    fun sendBackspace()
    fun sendTab()
    fun sendCtrl(char: Char)
    fun sendArrowUp()
    fun sendArrowDown()
    fun sendArrowRight()
    fun sendArrowLeft()
    fun sendHome()
    fun sendEnd()

    /** Send an interrupt signal to the model (ESC key). */
    fun interrupt()

    fun captureScreenFor(
        duration: Duration,
        columns: Int = 120,
        rows: Int = 64,
        mirrorRaw: Boolean = false,
    ): String

    /**
     * Current execution status, based on the last non-empty line of the CLI output.
     * Implementations may throw if the status cannot be determined from the output.
     */
    val status: AiCliExecutionStatus

    /**
     * Register a callback that will be invoked when the PTY's state is invalidated
     * (e.g., when [status] changes). Passing null clears the handler.
     */
    fun setOnInvalidation(handler: (() -> Unit)?)

    /**
     * Backend-specific session log extraction for the active PTY.
     * Implementations should read any needed metadata (e.g., session id) from
     * the thread's config.json adjacent to the working directory and parse the
     * latest available session log into SessionRecord entries. When not
     * applicable (e.g., UnitTest PTY), return an empty list.
     */
    fun sessionLogRecords(): List<Any>

    /**
     * Attempt to obtain the session ID for this PTY instance.
     * This may involve sending commands like /status and parsing the output.
     * Returns the session ID if found, or null if not available.
     * The implementation should handle all backend-specific details.
     */
    fun getSessionId(): String?

    /**
     * A short, human-readable preview of the command/args this PTY runs with
     * (used for UI headers). Should not execute any subprocesses.
     */
    fun commandPreview(): String

    /**
     * Persist the provided session ID to the thread's config in a backend-appropriate way.
     * Implementations should write to the config.json adjacent to the workspace (i.e.,
     * under the thread directory) using their own key (e.g., "codex_session_id").
     * Returns true on success, false on failure.
     */
    fun persistSessionId(sessionId: String): Boolean
}
