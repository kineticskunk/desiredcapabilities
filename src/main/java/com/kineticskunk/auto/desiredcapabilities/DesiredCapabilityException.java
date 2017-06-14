package com.kineticskunk.auto.desiredcapabilities;

public class DesiredCapabilityException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4991316726056463211L;
	private String message = null;

	public DesiredCapabilityException() {
		super();
	}

	public DesiredCapabilityException(String message) {
		super(message);
		this.message = message;
	}

	public DesiredCapabilityException(Throwable cause) {
		super(cause);
	}

	public DesiredCapabilityException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DesiredCapabilityException(String capabilityName, String capabilityValue) throws DesiredCapabilityException {
		if (capabilityValue == null) {
			throw new DesiredCapabilityException("DesiredCapability '" + capabilityName + "' is NULL" );
		}
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}