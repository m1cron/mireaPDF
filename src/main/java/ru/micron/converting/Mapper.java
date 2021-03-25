package ru.micron.converting;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Mapper {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static Map<String, String> map(Object object) {
    return mapper.convertValue(object, new TypeReference<>() {});
  }
}
