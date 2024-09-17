import { ProList } from '@ant-design/pro-components';
import { Space, Tag } from 'antd';
import { useEffect, useState } from 'react';
import { listInterfaceInfoByPageUsingPost } from '@/services/openapi-backend/aPixiangguanjiekou';
import { rule } from '@/services/ant-design-pro/api';
import { useNavigate } from 'react-router-dom';

export default () => {
  const [interfaces, setInterface] = useState<API.InterfaceInfo[]>([]);
  const navigate = useNavigate();
  useEffect(() => {
    listInterfaceInfoByPageUsingPost({pageSize: 1000}).then((res)=>{
      return setInterface(res?.data?.records);
      }
    )
  }, []);

  return (
    <ProList<API.InterfaceInfo>
      rowKey="id"
      headerTitle="基础列表"
      dataSource={interfaces}
      showActions="hover"
      editable={{
        onSave: async (key, record, originRow) => {
          console.log(key, record, originRow);
          return true;
        },
      }}
      onDataSourceChange={setInterface}
      metas={{
        title: {
          dataIndex: 'name',
        },
        description: {
          dataIndex: 'interfaceDescription',
        },
        subTitle: {
          render: (_, record) => {
            return (
              <Space size={0}>
                <Tag color="blue">{record.method}</Tag>
              </Space>
            );
          },
        },
        actions: {
          render: (_, record) => [
            <a
              onClick={() => {
                // 跳转至详情页，并将当前行的 id 作为参数传递
                navigate(`/details/${record.id}`);
              }}
              key="link"
            >
              详情
            </a>,
          ],
        },
      }}
    />
  );
};
