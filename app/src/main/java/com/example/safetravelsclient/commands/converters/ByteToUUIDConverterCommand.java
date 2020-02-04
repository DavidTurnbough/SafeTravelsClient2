package com.example.safetravelsclient.commands.converters;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.example.safetravelsclient.commands.interfaces.ResultInterface;

public class ByteToUUIDConverterCommand implements ResultInterface<UUID> {
    @Override
    public UUID execute() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(this.valueToConvert);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private byte[] valueToConvert;
    public byte[] getValueToConvert() {
        return this.valueToConvert;
    }
    public ByteToUUIDConverterCommand setValueToConvert(byte[] valueToConvert) {
        this.valueToConvert = valueToConvert;
        return this;
    }
}

