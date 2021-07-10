package ru.micron.converting;

import static ru.micron.ui.UIUtils.ICON_PNG;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResultMapHolder {

  public static final String REPORT = "report";
  public static final String WORK_NUMBER = "pracNumber";
  public static final String TARGET_CONTENT = "target";
  public static final String TEOR_CONTENT = "theory";
  public static final String STEP_BY_STEP = "stepByStep";
  public static final String CODE = "code";
  public static final String CONCLUSION = "conclusion";
  public static final String LITERATURE = "literature";
  public static final List<String> REPORT_FIELDS = List.of(
      TARGET_CONTENT, TEOR_CONTENT, STEP_BY_STEP, CODE, CONCLUSION, LITERATURE
  );
  private static final String IMG = "img";

  @Getter
  private final Map<String, Object> map = new HashMap<>();
  private final ObjectMapper mapper;

  @PostConstruct
  public void init() {
    map.put(IMG, convertImgToBase64());
  }

  public void save(Object object) {
    map.putAll(mapper.convertValue(object, new TypeReference<>() {}));
  }

  public void save(String key, String value) {
    map.put(key, value);
  }

  public void clear() {
    map.clear();
  }

  private String convertImgToBase64() {
    try {
      var inputStream = getClass().getResourceAsStream(ICON_PNG);
      return Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return null;
  }
}
