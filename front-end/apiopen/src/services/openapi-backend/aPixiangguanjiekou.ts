// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** addInterfaceInfo POST /interfaceInfo/add */
export async function addInterfaceInfoUsingPost(
  body: API.InterfaceInfoAddDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/interfaceInfo/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteInterfaceInfo POST /interfaceInfo/delete */
export async function deleteInterfaceInfoUsingPost(
  body: API.DeleteDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/interfaceInfo/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** editInterfaceInfo POST /interfaceInfo/edit */
export async function editInterfaceInfoUsingPost(
  body: API.InterfaceInfoUpdateDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/interfaceInfo/edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getInterfaceInfoVOById GET /interfaceInfo/get/vo */
export async function getInterfaceInfoVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getInterfaceInfoVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseInterfaceInfoVO_>('/interfaceInfo/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** invokeInterface POST /interfaceInfo/invoke */
export async function invokeInterfaceUsingPost(
  body: API.InterfaceInvokeDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseObject_>('/interfaceInfo/invoke', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listInterfaceInfoByPage POST /interfaceInfo/list/page */
export async function listInterfaceInfoByPageUsingPost(
  body: API.InterfaceInfoQueryDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageInterfaceInfo_>('/interfaceInfo/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listInterfaceInfoVOByPage POST /interfaceInfo/list/page/vo */
export async function listInterfaceInfoVoByPageUsingPost(
  body: API.InterfaceInfoQueryDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageInterfaceInfoVO_>('/interfaceInfo/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** offlineInterfaceInfo POST /interfaceInfo/offline */
export async function offlineInterfaceInfoUsingPost(
  body: API.InterfaceInfoOnlineDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/interfaceInfo/offline', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** onlineInterfaceInfo POST /interfaceInfo/online */
export async function onlineInterfaceInfoUsingPost(
  body: API.InterfaceInfoOnlineDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/interfaceInfo/online', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** updateInterfaceInfo POST /interfaceInfo/update */
export async function updateInterfaceInfoUsingPost(
  body: API.InterfaceInfoUpdateDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/interfaceInfo/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
