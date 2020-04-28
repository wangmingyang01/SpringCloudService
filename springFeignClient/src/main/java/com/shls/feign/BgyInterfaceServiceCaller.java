package com.shls.feign;

import com.shls.feign.vo.AssetPackage;
import com.shls.feign.vo.Auditingresultinfo;
import com.shls.feign.vo.AuditingresultinfoModel;
import com.shls.feign.vo.FinancingBatchInfo;
import feign.Feign;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Joe on 28/06/2018.
 */

@Service
@FeignClient(value = "BgyInterfaceService")//, fallback = BgyInterfaceServiceCallerHystrix.class
public interface BgyInterfaceServiceCaller
{

    /**
     * 接口4 推送付款单融资审核结果
     */
    @RequestMapping(value = "/bgyService/sendAuditingResultInfo", method = RequestMethod.POST)
    Object sendAuditingResultInfo(@RequestBody List<Auditingresultinfo> list);

    @RequestMapping(value = "/bgyService/sendFinancingBatchInfo", method = RequestMethod.POST)
    Object sendFinancingBatchInfo(@RequestBody List<FinancingBatchInfo> list);

    @RequestMapping(value = "/bgyService/sendAssetPackage", method = RequestMethod.POST)
    Object sendAssetPackage(@RequestBody AssetPackage assetPackage);


    //手动创建
    /*BgyInterfaceServiceCaller absSystemServiceByNoFileCaller = Feign.builder()
            .contract(new SpringMvcContract())
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(BgyInterfaceServiceCaller.class, "http://127.0.0.1:8080");*/


}
