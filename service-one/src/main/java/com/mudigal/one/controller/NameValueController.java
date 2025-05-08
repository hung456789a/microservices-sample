package com.mudigal.one.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mudigal.one.domain.NameValue;
import com.mudigal.one.service.NameValueService;

import reactor.core.publisher.Mono;

/**
 * 
 * @author Vijayendra Mudigal
 *
 */

//Đánh dấu lớp này là một controller trong Spring MVC và sẽ tự động trả về 
//các đối tượng như JSON hoặc XML thay vì trả về view
@RestController 
@RequestMapping(value = "/") //Định nghĩa đường dẫn gốc (/) cho controller này.
public class NameValueController {
	
	//Logger: Được sử dụng để ghi lại các thông báo (logs) trong ứng dụng
	Logger logger = Logger.getLogger(NameValueController.class.getName());
	//Đánh dấu rằng nameValueService sẽ được tự động tiêm (injected)
	//vào controller từ Spring container
	@Autowired
	private NameValueService nameValueService;


	@GetMapping
	public Mono<NameValue> getNameValue() {
		logger.debug("Inside getNameValue() method of NameValueController class");
		return nameValueService.getNameValue();
	}

}
// Định nghĩa một API Endpoint GET: Phương thức getNameValue() sẽ nhận yêu cầu HTTP GET từ client tại đường dẫn / và trả về một đối tượng NameValue.

// Hỗ trợ bất đồng bộ (Reactive Programming): Sử dụng Mono<NameValue>, phương thức này không chặn luồng chính khi lấy dữ liệu từ NameValueService, giúp ứng dụng xử lý các yêu cầu hiệu quả hơn trong môi trường có lượng truy cập lớn hoặc khi làm việc với cơ sở dữ liệu không đồng bộ.

// Tích hợp Service: Controller này kết nối với service (NameValueService) để lấy dữ liệu, làm việc theo mô hình Service-Layer.