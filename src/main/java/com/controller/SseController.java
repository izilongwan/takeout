package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.common.SseUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("/sse")
public class SseController {

    /**
     * sse 订阅消息
     */
    @GetMapping(path = "sub/{id}", produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
    public SseEmitter sub(@PathVariable String id) throws Exception {
        return SseUtil.connect(id);
    }

    /**
     * sse 发布消息
     */
    @GetMapping(path = "push/{id}")
    public void push(@PathVariable String id, String message) throws Exception {
        SseUtil.sendMessage(id, message);
    }

    @GetMapping("batchPush")
    public void batchSendMessage(String message) {
        SseUtil.batchSendMessage(message);
    }

    @GetMapping("batchPush/{ids}")
    public void batchIdsSendMessage(@PathVariable String ids, String message) {
        String[] arr = ids.split("_");

        HashSet<String> set = new HashSet<>(Arrays.asList(arr));
        // Set<String> set2 = Stream.of(arr).collect(Collectors.toSet());

        SseUtil.batchSendMessage(message, set);
    }

    @GetMapping(path = "disconnect/{id}")
    public void disconnect(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        request.startAsync();
        SseUtil.removeUser(id);
    }
}
