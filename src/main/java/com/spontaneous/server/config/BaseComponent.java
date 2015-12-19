package com.spontaneous.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base component contains a logger instance.
 */
public class BaseComponent {
    protected final Logger mLogger = LoggerFactory.getLogger(this.getClass());
}
