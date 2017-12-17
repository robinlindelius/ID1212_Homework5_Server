package se.kth.id1212.server.net;

import se.kth.id1212.common.Constants;
import se.kth.id1212.common.MessageType;

import java.io.Serializable;

/**
 * Created by Robin on 2017-11-16.
 */
class Message implements Serializable{
    private final String receivedMessage;
    private MessageType messageType;
    private String messageBody;

    /**
     * Constructor for a request
     * @param receivedMessage The request to construct
     */
    Message(String receivedMessage) {
        this.receivedMessage = receivedMessage;
        parseMessage(receivedMessage);
    }

    private void parseMessage(String receivedMessage) {
        try {
            String[] messageElements = receivedMessage.split(Constants.MESSAGE_DELIMITER);

            extractMessageType(messageElements);
            extractMessageBody(messageElements);
        }
        catch (Throwable throwable) {
            throw throwable;
        }
    }

    private void extractMessageType(String[] requestElements) {
        try {
            messageType = MessageType.valueOf(requestElements[Constants.MESSAGE_TYPE_INDEX].toUpperCase());
        }
        catch (Throwable failedToReadCommand) {
            throw failedToReadCommand;
        }
    }

    private void extractMessageBody(String[] messageElements) {
        if (messageElements.length > Constants.MESSAGE_BODY_INDEX) {
            this.messageBody = messageElements[Constants.MESSAGE_BODY_INDEX];
        }
    }

    /**
     * Returns the received message
     * @return receivedMessage
     */
    String getReceivedMessage() {
        return receivedMessage;
    }

    /**
     * Returns the message type
     * @return messageType
     */
    MessageType getMessageType() {
        return messageType;
    }

    /**
     * Returns the message body
     * @return messageBody
     */
    String getMessageBody() {
        return messageBody;
    }
}
