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
    data?: string;
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
    id?: string;
  };

  type deleteUserUsingPOSTParams = {
    /** id */
    id?: string;
  };

  type getByTeamIdUsingGETParams = {
    /** id */
    id?: string;
  };

  type getInterfaceInfoVOByIdUsingGETParams = {
    /** id */
    id?: string;
  };

  type InterfaceInfo = {
    createTime?: string;
    id?: string;
    interfaceDescription?: string;
    interfaceStatus?: number;
    isDelete?: number;
    method?: string;
    name?: string;
    requestHeader?: string;
    responseHeader?: string;
    updateTime?: string;
    url?: string;
    userId?: string;
  };

  type InterfaceInfoAddDTO = {
    interfaceDescription?: string;
    interfaceStatus?: number;
    method?: string;
    name?: string;
    requestHeader?: string;
    responseHeader?: string;
    url?: string;
  };

  type InterfaceInfoQueryDTO = {
    current?: number;
    id?: string;
    interfaceStatus?: number;
    method?: string;
    name?: string;
    pageSize?: number;
    searchText?: string;
    sortField?: string;
    sortOrder?: string;
    url?: string;
    userId?: string;
  };

  type InterfaceInfoUpdateDTO = {
    id?: string;
    interfaceDescription?: string;
    interfaceStatus?: number;
    method?: string;
    name?: string;
    requestHeader?: string;
    responseHeader?: string;
    url?: string;
  };

  type InterfaceInfoVO = {
    createTime?: string;
    id?: string;
    interfaceDescription?: string;
    interfaceStatus?: number;
    method?: string;
    name?: string;
    requestHeader?: string;
    responseHeader?: string;
    updateTime?: string;
    url?: string;
    userId?: string;
  };

  type listMyCreateTeamUsingGETParams = {
    id?: string;
    idList?: string[];
    maxNum?: number;
    searchText?: string;
    status?: number;
    userId?: string;
  };

  type listMyJoinTeamUsingGETParams = {
    id?: string;
    idList?: string[];
    maxNum?: number;
    searchText?: string;
    status?: number;
    userId?: string;
  };

  type listPageUsingGETParams = {
    id?: string;
    idList?: string[];
    maxNum?: number;
    searchText?: string;
    status?: number;
    userId?: string;
    /** pageNum */
    pageNum?: number;
    /** pageSize */
    pageSize?: number;
  };

  type listUsingGETParams = {
    id?: string;
    idList?: string[];
    maxNum?: number;
    searchText?: string;
    status?: number;
    userId?: string;
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
    current?: string;
    pages?: string;
    records?: InterfaceInfo[];
    size?: string;
    total?: string;
  };

  type PageInterfaceInfoVO_ = {
    current?: string;
    pages?: string;
    records?: InterfaceInfoVO[];
    size?: string;
    total?: string;
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
    id?: string;
    isDelete?: number;
    maxNum?: number;
    name?: string;
    password?: string;
    status?: number;
    updateTime?: string;
    userId?: string;
  };

  type TeamAddDTO = {
    description?: string;
    expireTime?: string;
    maxNum?: number;
    name?: string;
    password?: string;
    status?: number;
    userId?: string;
  };

  type TeamJoinDTO = {
    password?: string;
    teamId?: string;
  };

  type TeamQuitDTO = {
    teamId?: string;
  };

  type TeamUpdateDTO = {
    description?: string;
    expireTime?: string;
    id?: string;
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
    id?: string;
    maxNum?: number;
    name?: string;
    status?: number;
    updateTime?: string;
    userId?: string;
  };

  type User = {
    avatarUrl?: string;
    createTime?: string;
    email?: string;
    gender?: number;
    id?: string;
    isDelete?: number;
    phone?: string;
    planetCode?: string;
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
    id?: string;
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
