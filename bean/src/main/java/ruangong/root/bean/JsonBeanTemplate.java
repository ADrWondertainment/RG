package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@Data

/**
 * @author pangx

 *
            {
         "uid": 1,
         "name": "123",
         "type": "统计表",
         "startTime": "2022-05-24T16:00:00.000Z",
         "endTime": "2022-05-26T16:00:00.000Z",
         "contentLength": 2,
         "originContent": [
          {
           "type": "input",
           "isRequired": false,
           "description": "123",
           "value": []
          },
          {
           "type": "single-check",
           "isRequired": false,
           "description": "123",
           "value": [
            "123",
            "asd",
            "abc",
            "zbc"
           ]
          }
         ],
         "content": [
          {
           "id": "0",
           "type": "input",
           "value": {},
           "description": "123"
          },
          {
           "id": "1",
           "type": "single-check",
           "value": {
            "123": 0,
            "asd": 0,
            "abc": 0,
            "zbc": 0
           },
           "description": "123"
          }
         ]
        }
*/

public class JsonBeanTemplate {
    private int uid;
    private String name;
    private String type;
    private String startTime;
    private String endTime;
    private String description;
    private int contentLength;
    private String originContent;
    private List<JsonBeanTemplateContentsContent> content;
}
