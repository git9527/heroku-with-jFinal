package me.bird.qiniu;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.QiNiuUtil;

import org.junit.Assert;
import org.junit.Test;

public class TestQiNiuUtils {

	@Test
	public void test() {
		BaseConsts.QINIU_SPACE_NAME = "my-bucket";
		BaseConsts.QINIU_APP_KEY = "MY_ACCESS_KEY";
		BaseConsts.QINIU_SECRET_KEY = "MY_SECRET_KEY";
		Assert.assertEquals("MY_ACCESS_KEY:YzEwZTI4N2YyYjFlN2Y1NDdiMjBhOWViY2UyYWFkYTI2YWIyMGVmMg==:eyJzY29wZSI6Im15LWJ1Y2tldDpzdW5mbG93ZXIuanBnIiwiZGVhZGxpbmUiOjE0NTE0OTEyMDAsInJldHVybkJvZHkiOiJ7XCJuYW1lXCI6JChmbmFtZSksXCJzaXplXCI6JChmc2l6ZSksXCJ3XCI6JChpbWFnZUluZm8ud2lkdGgpLFwiaFwiOiQoaW1hZ2VJbmZvLmhlaWdodCksXCJoYXNoXCI6JChldGFnKX0ifQ==", QiNiuUtil.getUploadToken("sunflower.jpg"));
	}
}
