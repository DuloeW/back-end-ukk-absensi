package org.acme.ukk.absensi.core.util.jackson;

import java.io.IOException;

import org.acme.ukk.absensi.core.jwt.TokenResponse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TokenSerialize extends JsonSerializer<TokenResponse> {

  @Override
  public void serialize(
    TokenResponse token,
    JsonGenerator jsonGenerator,
    SerializerProvider arg2
  ) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("token", token.getToken());
    jsonGenerator.writeStringField("message", token.getMessage());
    jsonGenerator.writeEndObject();
  }
}
