package fr.quantumadventure.utils.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Logger {
    private static final Map<String, Logger> LOGGERS = new ConcurrentHashMap<>();
    private static final Map<LogLevel, LogLevelInfo> LOG_COLORS;

    static {
        Map<LogLevel, LogLevelInfo> colors = new HashMap<>();
        colors.put(LogLevel.INFO, new LogLevelInfo(AnsiColor.WHITE));
        colors.put(LogLevel.WARN, new LogLevelInfo(AnsiColor.YELLOW));
        colors.put(LogLevel.ERROR, new LogLevelInfo(AnsiColor.RED));
        colors.put(LogLevel.DEBUG, new LogLevelInfo(AnsiColor.GRAY));
        colors.put(LogLevel.SUCCESS, new LogLevelInfo(AnsiColor.GREEN));
        LOG_COLORS = Collections.unmodifiableMap(colors);
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String TIMESTAMP_COLOR = AnsiColor.DARK_CYAN;
    private static final String LOGGER_NAME_COLOR = AnsiColor.MAGENTA;
    private static final String DEFAULT_MESSAGE_COLOR = AnsiColor.WHITE;
    private static final Object CONSOLE_LOCK = new Object();

    private final String name;

    private Logger(final String name) {
        this.name = name;
    }

    public static Logger getLogger(final Class<?> clazz) {
        return getLogger(clazz.getSimpleName());
    }

    public static Logger getLogger(final String name) {
        return LOGGERS.computeIfAbsent(name, Logger::new);
    }

    public static ColoredText colored(final String text, final String color) {
        return new ColoredText(text, color);
    }

    private void log(final LogLevel level, final Object... messageParts) {
        synchronized (CONSOLE_LOCK) {
            final String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
            final LogLevelInfo levelInfo = LOG_COLORS.get(level);

            final StringBuilder message = new StringBuilder()
                    .append(TIMESTAMP_COLOR).append("[").append(timestamp).append("] ")
                    .append(levelInfo.getColor()).append("[").append(level.getPrefix()).append("] ")
                    .append(LOGGER_NAME_COLOR).append("[").append(name).append("] ");

            for (final Object part : messageParts) {
                if (part instanceof ColoredText) {
                    final ColoredText coloredText = (ColoredText) part;
                    message.append(coloredText.getColor())
                            .append(coloredText.getText())
                            .append(DEFAULT_MESSAGE_COLOR);
                } else {
                    message.append(DEFAULT_MESSAGE_COLOR)
                            .append(part.toString());
                }
            }

            message.append(AnsiColor.RESET);
            if (level == LogLevel.ERROR) {
                System.err.println(message);
            } else {
                System.out.println(message);
            }
        }
    }

    public void info(final Object... messageParts) {
        log(LogLevel.INFO, messageParts);
    }

    public void warn(final Object... messageParts) {
        log(LogLevel.WARN, messageParts);
    }

    public void error(final Object... messageParts) {
        log(LogLevel.ERROR, messageParts);
    }

    public void debug(final Object... messageParts) {
        log(LogLevel.DEBUG, messageParts);
    }

    public void success(final Object... messageParts) {
        log(LogLevel.SUCCESS, messageParts);
    }

    public void error(final String message, final Throwable throwable) {
        error(message);
        throwable.printStackTrace(System.err);
    }

}