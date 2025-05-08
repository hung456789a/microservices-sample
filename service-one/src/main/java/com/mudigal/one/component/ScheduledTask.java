// Đoạn code này là một Scheduled Task trong Spring Boot – tức là một tác vụ chạy định kỳ. Cụ thể, nó được thiết kế để:

// Cứ mỗi 60 giây, lấy dữ liệu NameValue, sinh UUID mới, cập nhật lại value và lưu lại.
package com.mudigal.one.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mudigal.one.domain.NameValue;
import com.mudigal.one.service.NameValueService;

/**
 * 
 * @author Vijayendra Mudigal
 *
 */
@Component //Biến lớp ScheduledTask thành một Spring Bean.
public class ScheduledTask {
	//Dùng để log thông tin khi tác vụ chạy.
	private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

	private NameValueService nameValueService;
	// Lấy một instance của NameValueService từ Spring context (dependency injection).
	@Autowired
	public ScheduledTask(NameValueService nameValueService) {
		this.nameValueService = nameValueService;
	}

	// Chạy generateUUID() cách nhau mỗi 60 giây kể từ khi lần trước kết thúc.
	@Scheduled(fixedDelay = 60 * 1000)
	public void generateUUID() {
		logger.debug("Triggered scheduled task to update the service's value.");
		nameValueService.getNameValue().subscribe(data -> {
			NameValue nameValue = data;
			nameValue.setValue(nameValueService.generateUUID().getValue());
			nameValueService.updateNameValue(nameValue, false);
		});
	}
	
}

// 60 giây trôi qua →
//   gọi getNameValue() →
//     nhận được NameValue →
//       sinh UUID mới →
//         cập nhật lại →
//           lưu lại
