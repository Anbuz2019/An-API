import { useParams } from 'react-router-dom';
import { useEffect, useState, React } from 'react';
import { Descriptions, Typography, Input, Button, message } from 'antd';
import {
  getInterfaceInfoVoByIdUsingGet, invokeInterfaceUsingPost,
} from '@/services/openapi-backend/aPixiangguanjiekou';

const Details = () => {
  const { id } = useParams();  // 获取动态参数 :id
  const [interfaceInfo, setInterface] = useState<API.InterfaceInfo>();
  const [requestParams, setRequestParams] = useState<string>('');
  const [result, setResult] = useState<string>('');
  const { Title } = Typography;
  const { TextArea } = Input;

  useEffect(() => {
    getInterfaceInfoVoByIdUsingGet({id: id}).then((res)=>{
        console.log(res);
        return setInterface(res?.data);
      }
    )
  }, []);

  const items: DescriptionsProps['items'] = [
    {
      key: '1',
      label: <span style={{ fontSize: '14px' }}>名称</span>,
      children: <span style={{ fontSize: '18px' }}>{interfaceInfo?.name}</span>,
    },
    {
      key: '2',
      label: <span style={{ fontSize: '14px' }}>路径</span>,
      children: <span style={{ fontSize: '18px' }}>{interfaceInfo?.url}</span>,
    },
    {
      key: '3',
      label: <span style={{ fontSize: '14px' }}>类型</span>,
      children: <span style={{ fontSize: '18px' }}>{interfaceInfo?.method}</span>,
    },
    {
      key: '4',
      label: 'Address',
      label: <span style={{ fontSize: '14px' }}>请求参数</span>,
      children: <span style={{ fontSize: '18px' }}>{interfaceInfo?.requestParams}</span>,
    },
    {
      key: '5',
      label: <span style={{ fontSize: '14px' }}>积分价格</span>,
      children: <span style={{ fontSize: '18px' }}>{interfaceInfo?.costScore}</span>,
    },
  ];

  const handleButtonClick = async () => {
    try {
      JSON.parse(requestParams);
    } catch (e) {
      message.error("格式错误！")
      return;
    }
    const param = {
      requestParams: JSON.parse(requestParams),
      interfaceId: id,
    }
    const res = await invokeInterfaceUsingPost(param);
    if (res===null){
      message.error("调用失败");
    }else{
      // alert(res)
      setResult(res.data);
    }
  };

  return (
    <div>
      {/*<h1>接口详情页</h1>*/}
      {interfaceInfo ? (
        <>
          <div>
            <Title level={4}>接口详情</Title>
            <p />
            <Descriptions layout="vertical" items={items} size={'middle'}
                          style={{
                            marginTop: "40px",
                            fontSize: "large"
                          }} />
          </div>
          <div style={{ marginTop: "90px", width: '800px' }}>
            <Title level={4}>参数示例</Title>
            <p />
            <TextArea showCount maxLength={100}
                      style={{ height: 150 }}
                      value={interfaceInfo.requestExample}
                      disabled={true}
            />
          </div>
          <div style={{ marginTop: "40px", width: '800px' }}>
            <Title level={4}>在线调用</Title>
            <p />
            <TextArea showCount maxLength={100}
                      style={{ height: 120 }}
                      placeholder="输入请求参数"
                      value={requestParams}
                      onChange={(e) => setRequestParams(e.target.value)}
            />
            <Button
              type="primary"
              style={{ marginTop: '10px', width: '200px', marginLeft: '300px' }}
              onClick={handleButtonClick}
            >
              提交
            </Button>
          </div>
          {result !== null && result.length !== 0 && (  // 仅在 result 有值时渲染
            <div style={{ marginTop: "40px", width: '800px' }}>
              <Title level={4}>返回值</Title>
              <p />
              <TextArea
                showCount
                maxLength={100}
                value={JSON.stringify(result)}
                disabled={true}
                style={{ height: 120 }}
              />
            </div>
          )}
        </>
      ) : (
        <p>未找到接口信息</p>
      )}
    </div>
  );
};

export default Details;
