package com.spontaneous.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base component contains a logger object.
 */
public class BaseComponent {
    protected Logger mLogger = LoggerFactory.getLogger(this.getClass());
}
