// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** add POST /team/add */
export async function addUsingPost(body: API.TeamAddDTO, options?: { [key: string]: any }) {
  return request<API.BaseResponseLong_>('/team/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** delete POST /team/delete */
export async function deleteUsingPost(body: API.TeamQuitDTO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean_>('/team/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getByTeamId GET /team/get */
export async function getByTeamIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getByTeamIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseTeam_>('/team/get', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** join POST /team/join */
export async function joinUsingPost(body: API.TeamJoinDTO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean_>('/team/join', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** list GET /team/list */
export async function listUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListTeamUserVO_>('/team/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listMyCreateTeam GET /team/list/my/create */
export async function listMyCreateTeamUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listMyCreateTeamUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListTeamUserVO_>('/team/list/my/create', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listMyJoinTeam GET /team/list/my/join */
export async function listMyJoinTeamUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listMyJoinTeamUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListTeamUserVO_>('/team/list/my/join', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listPage GET /team/list/page */
export async function listPageUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listPageUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListTeam_>('/team/list/page', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** quit POST /team/quit */
export async function quitUsingPost(body: API.TeamQuitDTO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean_>('/team/quit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** update POST /team/update */
export async function updateUsingPost(body: API.TeamUpdateDTO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean_>('/team/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
