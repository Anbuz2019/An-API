declare namespace API {
  type BaseResponseBoolean_ = {
    code?: number;
    data?: boolean;
    description?: string;
    msg?: string;
  };

  type BaseResponseInt_ = {
    code?: number;
    data?: number;
    description?: string;
    msg?: string;
  };

  type BaseResponseInterfaceInfoVO_ = {
    code?: number;
    data?: InterfaceInfoVO;
    description?: string;
    msg?: string;
  };

  type BaseResponseListTeam_ = {
    code?: number;
    data?: Team[];
    description?: string;
    msg?: string;
  };

  type BaseResponseListTeamUserVO_ = {
    code?: number;
    data?: TeamUserVO[];
    description?: string;
    msg?: string;
  };

  type BaseResponseListUser_ = {
    code?: number;
    data?: User[];
    description?: string;
    msg?: string;
  };

  type BaseResponseLong_ = {
    code?: number;
    data?: number;
    description?: string;
    msg?: string;
  };

  type BaseResponseObject_ = {
    code?: number;
    data?: Record<string, any>;
    description?: string;
    msg?: string;
  };

  type BaseResponsePageInterfaceInfo_ = {
    code?: number;
    data?: PageInterfaceInfo_;
    description?: string;
    msg?: string;
  };

  type BaseResponsePageInterfaceInfoVO_ = {
    code?: number;
    data?: PageInterfaceInfoVO_;
    description?: string;
    msg?: string;
  };

  type BaseResponseTeam_ = {
    code?: number;
    data?: Team;
    description?: string;
    msg?: string;
  };

  type BaseResponseUser_ = {
    code?: number;
    data?: User;
    description?: string;
    msg?: string;
  };

  type DeleteDTO = {
    id?: number;
  };

  type deleteUserUsingPOSTParams = {
    /** id */
    id?: number;
  };

  type getByTeamIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getInterfaceInfoVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type InterfaceInfo = {
    costScore?: number;
    createTime?: string;
    id?: number;
    interfaceDescription?: string;
    interfaceStatus?: number;
    isDelete?: number;
    method?: string;
    name?: string;
    requestExample?: Record<string, any>;
    requestHeader?: string;
    requestParams?: Record<string, any>;
    responseHeader?: string;
    responseParams?: Record<string, any>;
    updateTime?: string;
    url?: string;
    userId?: number;
  };

  type InterfaceInfoAddDTO = {
    costScore?: number;
    interfaceDescription?: string;
    interfaceStatus?: number;
    method?: string;
    name?: string;
    requestExample?: Record<string, any>;
    requestHeader?: string;
    requestParams?: Record<string, any>;
    responseHeader?: string;
    responseParams?: Record<string, any>;
    url?: string;
  };

  type InterfaceInfoOnlineDTO = {
    id?: number;
  };

  type InterfaceInfoQueryDTO = {
    current?: number;
    id?: number;
    interfaceStatus?: number;
    method?: string;
    name?: string;
    pageSize?: number;
    searchText?: string;
    sortField?: string;
    sortOrder?: string;
    url?: string;
    userId?: number;
  };

  type InterfaceInfoUpdateDTO = {
    costScore?: number;
    id?: number;
    interfaceDescription?: string;
    interfaceStatus?: number;
    method?: string;
    name?: string;
    requestExample?: Record<string, any>;
    requestHeader?: string;
    requestParams?: Record<string, any>;
    responseHeader?: string;
    responseParams?: Record<string, any>;
    url?: string;
  };

  type InterfaceInfoVO = {
    costScore?: number;
    createTime?: string;
    id?: number;
    interfaceDescription?: string;
    interfaceStatus?: number;
    method?: string;
    name?: string;
    requestExample?: Record<string, any>;
    requestHeader?: string;
    requestParams?: Record<string, any>;
    responseHeader?: string;
    responseParams?: Record<string, any>;
    updateTime?: string;
    url?: string;
    userId?: number;
  };

  type InterfaceInvokeDTO = {
    interfaceId?: number;
    requestParams?: Record<string, any>;
  };

  type listMyCreateTeamUsingGETParams = {
    id?: number;
    idList?: number[];
    maxNum?: number;
    searchText?: string;
    status?: number;
    userId?: number;
  };

  type listMyJoinTeamUsingGETParams = {
    id?: number;
    idList?: number[];
    maxNum?: number;
    searchText?: string;
    status?: number;
    userId?: number;
  };

  type listPageUsingGETParams = {
    id?: number;
    idList?: number[];
    maxNum?: number;
    searchText?: string;
    status?: number;
    userId?: number;
    /** pageNum */
    pageNum?: number;
    /** pageSize */
    pageSize?: number;
  };

  type listUsingGETParams = {
    id?: number;
    idList?: number[];
    maxNum?: number;
    searchText?: string;
    status?: number;
    userId?: number;
  };

  type matchUsersUsingGETParams = {
    /** num */
    num?: number;
  };

  type ModelAndView = {
    empty?: boolean;
    model?: Record<string, any>;
    modelMap?: Record<string, any>;
    reference?: boolean;
    status?:
      | 'CONTINUE'
      | 'SWITCHING_PROTOCOLS'
      | 'PROCESSING'
      | 'CHECKPOINT'
      | 'OK'
      | 'CREATED'
      | 'ACCEPTED'
      | 'NON_AUTHORITATIVE_INFORMATION'
      | 'NO_CONTENT'
      | 'RESET_CONTENT'
      | 'PARTIAL_CONTENT'
      | 'MULTI_STATUS'
      | 'ALREADY_REPORTED'
      | 'IM_USED'
      | 'MULTIPLE_CHOICES'
      | 'MOVED_PERMANENTLY'
      | 'FOUND'
      | 'MOVED_TEMPORARILY'
      | 'SEE_OTHER'
      | 'NOT_MODIFIED'
      | 'USE_PROXY'
      | 'TEMPORARY_REDIRECT'
      | 'PERMANENT_REDIRECT'
      | 'BAD_REQUEST'
      | 'UNAUTHORIZED'
      | 'PAYMENT_REQUIRED'
      | 'FORBIDDEN'
      | 'NOT_FOUND'
      | 'METHOD_NOT_ALLOWED'
      | 'NOT_ACCEPTABLE'
      | 'PROXY_AUTHENTICATION_REQUIRED'
      | 'REQUEST_TIMEOUT'
      | 'CONFLICT'
      | 'GONE'
      | 'LENGTH_REQUIRED'
      | 'PRECONDITION_FAILED'
      | 'PAYLOAD_TOO_LARGE'
      | 'REQUEST_ENTITY_TOO_LARGE'
      | 'URI_TOO_LONG'
      | 'REQUEST_URI_TOO_LONG'
      | 'UNSUPPORTED_MEDIA_TYPE'
      | 'REQUESTED_RANGE_NOT_SATISFIABLE'
      | 'EXPECTATION_FAILED'
      | 'I_AM_A_TEAPOT'
      | 'INSUFFICIENT_SPACE_ON_RESOURCE'
      | 'METHOD_FAILURE'
      | 'DESTINATION_LOCKED'
      | 'UNPROCESSABLE_ENTITY'
      | 'LOCKED'
      | 'FAILED_DEPENDENCY'
      | 'TOO_EARLY'
      | 'UPGRADE_REQUIRED'
      | 'PRECONDITION_REQUIRED'
      | 'TOO_MANY_REQUESTS'
      | 'REQUEST_HEADER_FIELDS_TOO_LARGE'
      | 'UNAVAILABLE_FOR_LEGAL_REASONS'
      | 'INTERNAL_SERVER_ERROR'
      | 'NOT_IMPLEMENTED'
      | 'BAD_GATEWAY'
      | 'SERVICE_UNAVAILABLE'
      | 'GATEWAY_TIMEOUT'
      | 'HTTP_VERSION_NOT_SUPPORTED'
      | 'VARIANT_ALSO_NEGOTIATES'
      | 'INSUFFICIENT_STORAGE'
      | 'LOOP_DETECTED'
      | 'BANDWIDTH_LIMIT_EXCEEDED'
      | 'NOT_EXTENDED'
      | 'NETWORK_AUTHENTICATION_REQUIRED';
    view?: View;
    viewName?: string;
  };

  type PageInterfaceInfo_ = {
    current?: number;
    pages?: number;
    records?: InterfaceInfo[];
    size?: number;
    total?: number;
  };

  type PageInterfaceInfoVO_ = {
    current?: number;
    pages?: number;
    records?: InterfaceInfoVO[];
    size?: number;
    total?: number;
  };

  type recommendUsersUsingGETParams = {
    /** pageNum */
    pageNum?: number;
    /** pageSize */
    pageSize?: number;
  };

  type searchUsersByTagsUsingGETParams = {
    /** tagNameList */
    tagNameList?: string[];
  };

  type searchUsersUsingGETParams = {
    /** username */
    username?: string;
  };

  type Team = {
    createTime?: string;
    description?: string;
    expireTime?: string;
    id?: number;
    isDelete?: number;
    maxNum?: number;
    name?: string;
    password?: string;
    status?: number;
    updateTime?: string;
    userId?: number;
  };

  type TeamAddDTO = {
    description?: string;
    expireTime?: string;
    maxNum?: number;
    name?: string;
    password?: string;
    status?: number;
    userId?: number;
  };

  type TeamJoinDTO = {
    password?: string;
    teamId?: number;
  };

  type TeamQuitDTO = {
    teamId?: number;
  };

  type TeamUpdateDTO = {
    description?: string;
    expireTime?: string;
    id?: number;
    name?: string;
    password?: string;
    status?: number;
  };

  type TeamUserVO = {
    createTime?: string;
    createUser?: UserVO;
    description?: string;
    expireTime?: string;
    hasJoin?: boolean;
    hasJoinNum?: number;
    id?: number;
    maxNum?: number;
    name?: string;
    status?: number;
    updateTime?: string;
    userId?: number;
  };

  type User = {
    accessKey?: string;
    avatarUrl?: string;
    createTime?: string;
    email?: string;
    gender?: number;
    id?: number;
    isDelete?: number;
    phone?: string;
    secretKey?: string;
    tags?: string;
    updateTime?: string;
    userAccount?: string;
    userPassword?: string;
    userRole?: number;
    userStatus?: number;
    username?: string;
  };

  type UserLoginDTO = {
    userAccount?: string;
    userPassword?: string;
  };

  type UserRegisterDTO = {
    checkPasswd?: string;
    passwd?: string;
    userAccount?: string;
    username?: string;
  };

  type UserVO = {
    avatarUrl?: string;
    createTime?: string;
    email?: string;
    gender?: number;
    id?: number;
    phone?: string;
    planetCode?: string;
    tags?: string;
    updateTime?: string;
    userAccount?: string;
    userRole?: number;
    userStatus?: number;
    username?: string;
  };

  type View = {
    contentType?: string;
  };
}
