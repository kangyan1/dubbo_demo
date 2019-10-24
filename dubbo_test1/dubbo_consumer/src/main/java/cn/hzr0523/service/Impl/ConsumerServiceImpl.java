package cn.hzr0523.service.Impl;

import cn.hzr0523.service.ConsumerService;
import cn.hzr0523.service.ProviderService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author yan.kang@hand-china.com
 * @date 2019/10/24 11:08
 */
@Service
@Component
public class ConsumerServiceImpl implements ConsumerService {
    @Reference
    private ProviderService providerService;

    @Override
    public void receive() {
        providerService.send();
    }
}
