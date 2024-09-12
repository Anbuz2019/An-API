// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** getCurrentUser GET /user/current */
export async function getCurrentUserUsingGet(options?: { [key: string]: any }) {
  return request<API.BaseResponseUser_>('/user/current', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 通过id删除用户 POST /user/delete */
export async function deleteUserUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteUserUsingPOSTParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/user/delete', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 用户登录接口 POST /user/login */
export async function loginUsingPost(body: API.UserLoginDTO, options?: { [key: string]: any }) {
  return request<API.BaseResponseUser_>('/user/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 用户登出 POST /user/logout */
export async function userLogoutUsingPost(options?: { [key: string]: any }) {
  return request<API.BaseResponseInt_>('/user/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

/** matchUsers GET /user/match */
export async function matchUsersUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.matchUsersUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListUser_>('/user/match', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 主页用户推荐 GET /user/recommend */
export async function recommendUsersUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.recommendUsersUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListUser_>('/user/recommend', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 用户注册接口 POST /user/register */
export async function registerUsingPost(
  body: API.UserRegisterDTO,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/user/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 根据用户名查询用户 GET /user/search */
export async function searchUsersUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.searchUsersUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListUser_>('/user/search', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 搜索指定标签的用户 GET /user/search/tags */
export async function searchUsersByTagsUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.searchUsersByTagsUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListUser_>('/user/search/tags', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 用户信息修改 POST /user/update */
export async function updateUserUsingPost(body: API.User, options?: { [key: string]: any }) {
  return request<API.BaseResponseInt_>('/user/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
