interface LoginReq {
    userName: string
    userPassword: string
    genericReq: GenericReq
}

interface GenericReq {
    phone: number
    maill: string
    sessionIdRes: SessionIdRes
}

interface SessionIdRes {
    sessionId: string
}

export { LoginReq, GenericReq, SessionIdRes }