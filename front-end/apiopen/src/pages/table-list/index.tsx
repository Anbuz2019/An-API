import { removeRule, rule } from '@/services/ant-design-pro/api';
import type { ActionType, ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import {
  FooterToolbar,
  PageContainer,
  ProDescriptions,
  ProTable,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl, useRequest } from '@umijs/max';
import { Button, Drawer, Input, message } from 'antd';
import React, { useCallback, useRef, useState } from 'react';
import CreateForm from './components/CreateForm';
import UpdateForm from './components/UpdateForm';
import UpdateModal from "@/pages/table-list/components/UpdateModal";
import {addInterfaceInfoUsingPost,
  updateInterfaceInfoUsingPost,
  deleteInterfaceInfoUsingPost,
  onlineInterfaceInfoUsingPost,
  offlineInterfaceInfoUsingPost} from "@/services/openapi-backend/aPixiangguanjiekou";
import CreateModal from "@/pages/table-list/components/CreateModal";
import {PlusOutlined} from "@ant-design/icons";

const TableList: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const [showDetail, setShowDetail] = useState<boolean>(false);
  const [currentRow, setCurrentRow] = useState<API.RuleListItem>();
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const [selectedRowsState, setSelectedRows] = useState<API.RuleListItem[]>([]);

  /**
   * @en-US International configuration
   * @zh-CN 国际化配置
   * */
  const intl = useIntl();

  const [messageApi, contextHolder] = message.useMessage();

  const { run: delRun, loading } = useRequest(removeRule, {
    manual: true,
    onSuccess: () => {
      setSelectedRows([]);
      actionRef.current?.reloadAndRest?.();

      messageApi.success('Deleted successfully and will refresh soon');
    },
    onError: () => {
      messageApi.error('Delete failed, please try again');
    },
  });

  const columns: ProColumns<API.InterfaceInfoVO>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'index',
    },
    {
      title: '接口名称',
      dataIndex: 'name',
      valueType: 'text',
    },
    {
      title: '描述',
      dataIndex: 'interfaceDescription',
      valueType: 'textarea',
    },
    {
      title: '请求方法',
      dataIndex: 'method',
      hideInForm: false,
      valueType: 'text',
    },
    {
      title: '请求参数',
      dataIndex: 'requestParams',
      valueType: 'jsonCode',
      hideInForm: false,
    },
    {
      title: '响应参数',
      dataIndex: 'responseParams',
      valueType: 'jsonCode',
      hideInForm: false,
    },
    {
      title: '请求示例',
      dataIndex: 'requestExample',
      valueType: 'jsonCode',
      hideInForm: false,
    },
    {
      title: '消耗积分',
      dataIndex: 'costScore',
      valueType: 'digit',
      hideInForm: false,
    },
    {
      title: 'url',
      dataIndex: 'url',
      valueType: 'text',
    },
    // {
    //   title: '请求头',
    //   dataIndex: 'requestHeader',
    //   hideInForm: false,
    //   valueType: 'text',
    // },
    // {
    //   title: '响应头',
    //   dataIndex: 'responseHeader',
    //   hideInForm: false,
    //   valueType: 'text',
    // },
    {
      title: '状态',
      dataIndex: 'interfaceStatus',
      hideInForm: true,
      valueEnum: {
        0: {
          text: '关闭',
          status: 'Default',
        },
        1: {
          text: '开启',
          status: 'Processing',
        },
      },
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => {
        const operations = [];

        // 修改按钮
        operations.push(
          <a
            key="edit"
            onClick={() => {
              handleUpdateModalVisible(true);
              setCurrentRow(record);
            }}
          >
            修改
          </a>
        );

        // 发布按钮，仅当状态为 0 时显示
        if (record.interfaceStatus === 0) {
          operations.push(
            <Button
              type="text"
              key="publish"
              onClick={() => {
                handleOnline(record);
              }}
            >
              发布
            </Button>
          );
        }

        // 下线按钮，仅当状态为 1 时显示
        if (record.interfaceStatus === 1) {
          operations.push(
            <Button
              type="text"
              key="offline"
              danger
              onClick={() => {
                handleOffline(record);
              }}
            >
              下线
            </Button>
          );
        }

        // 删除按钮
        operations.push(
          <Button
            type="text"
            key="delete"
            danger
            onClick={() => {
              handleRemove(record);
            }}
          >
            删除
          </Button>
        );

        return operations;
      }
    }

  ];

  /**
   * 发布接口
   *
   * @param record
   */
  const handleOnline = async (record: API.InterfaceInfoOnlineDTO) => {
    const hide = message.loading('发布中');
    if (!record) return true;
    try {
      const res = await onlineInterfaceInfoUsingPost({
        id: record.id
      });
      hide();
      if (res.code===0 && res.data===true){
        message.success('操作成功');
        actionRef.current?.reload();
        return true;
      }else{
        throw new Error(res.msg);
      }
    } catch (error: any) {
      hide();
      message.error('操作失败，' + error.message);
      return false;
    }
  };

  /**
   * 下线接口
   *
   * @param record
   */
  const handleOffline = async (record: API.InterfaceInfoOnlineDTO) => {
    const hide = message.loading('发布中');
    if (!record) return true;
    try {
      const res = await offlineInterfaceInfoUsingPost({
        id: record.id
      });
      hide();
      if (res.code===0 && res.data===true){
        message.success('操作成功');
        actionRef.current?.reload();
        return true;
      }else{
        throw new Error(res.msg);
      }
    } catch (error: any) {
      hide();
      message.error('操作失败，' + error.message);
      return false;
    }
  };

  /**
   *  Delete node
   * @zh-CN 删除节点
   *
   * @param record
   */
  const handleRemove = async (record: API.InterfaceInfo) => {
    const hide = message.loading('正在删除');
    if (!record) return true;
    try {
      const res = await deleteInterfaceInfoUsingPost({
        id: record.id
      });
      hide();
      if (res?.code === 0){
        message.success('删除成功');
        actionRef.current?.reload();
        return true;
      } else{
        throw new Error(res.msg);
      }
    } catch (error: any) {
      hide();
      message.error('删除失败，' + error.message);
      return false;
    }
  };

  async function handleUpdate(value: API.InterfaceInfo) {
    console.log(value);
    const res = await updateInterfaceInfoUsingPost(value);
    if (res.code === 0){
      return res.data;
    }else{
      throw new ("更新失败");
    }
  }

  async function handleAdd(values: API.InterfaceInfo) {
    console.log(values);
    const res = await addInterfaceInfoUsingPost(values);
    if (res.code === 0){
      return res.data;
    }else{
      throw new Error("添加失败");
    }
  }

  return (
    <PageContainer>
      {contextHolder}
      <ProTable<API.RuleListItem, API.PageParams>
        headerTitle={intl.formatMessage({
          id: 'pages.searchTable.title',
          defaultMessage: 'Enquiry form',
        })}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              handleModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={rule}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              <FormattedMessage id="pages.searchTable.chosen" defaultMessage="Chosen" />{' '}
              <a style={{ fontWeight: 600 }}>{selectedRowsState.length}</a>{' '}
              <FormattedMessage id="pages.searchTable.item" defaultMessage="项" />
              &nbsp;&nbsp;
              <span>
                <FormattedMessage
                  id="pages.searchTable.totalServiceCalls"
                  defaultMessage="Total number of service calls"
                />{' '}
                {selectedRowsState.reduce((pre, item) => pre + item.callNo!, 0)}{' '}
                <FormattedMessage id="pages.searchTable.tenThousand" defaultMessage="万" />
              </span>
            </div>
          }
        >
          <Button
            loading={loading}
            onClick={() => {
              handleRemove(selectedRowsState);
            }}
          >
            <FormattedMessage
              id="pages.searchTable.batchDeletion"
              defaultMessage="Batch deletion"
            />
          </Button>
          <Button type="primary">
            <FormattedMessage
              id="pages.searchTable.batchApproval"
              defaultMessage="Batch approval"
            />
          </Button>
        </FooterToolbar>
      )}

      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.RuleListItem>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.RuleListItem>[]}
          />
        )}
      </Drawer>

      <UpdateModal
        columns={columns}
        onSubmit={async (value) => {
          const success = await handleUpdate(value);
          if (success) {
            handleUpdateModalVisible(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalVisible(false);
          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
        visible={updateModalVisible}
        values={currentRow || {}}
      />

      <CreateModal
        columns={columns}
        onCancel={()=>{
          handleModalVisible(false);
        }}
        onSubmit={(values)=>{
          handleAdd(values);
          handleModalVisible(false);
          if (actionRef.current) {
            actionRef.current.reload();
          }
        }}
        visible={createModalVisible}/>

    </PageContainer>
  );
};

export default TableList;
