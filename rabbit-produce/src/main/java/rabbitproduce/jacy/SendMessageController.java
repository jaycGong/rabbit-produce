package rabbitproduce.jacy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("/api")
public class SendMessageController {
    @Autowired
   SenderService  rabbitProductorService;

    @GetMapping("/sendMsg")
    public void sendMsg(@RequestParam("msg") String msg) throws IOException, TimeoutException {
        rabbitProductorService.Send(msg,null);
    }
}
