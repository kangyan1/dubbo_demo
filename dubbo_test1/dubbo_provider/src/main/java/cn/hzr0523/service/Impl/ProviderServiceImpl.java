package cn.hzr0523.service.Impl;

import cn.hzr0523.service.ProviderService;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * description
 *
 * @author yan.kang@hand-china.com
 * @date 2019/10/24 10:57
 */
@Service
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String send() {
        return "hello,you are success";
    }
}
