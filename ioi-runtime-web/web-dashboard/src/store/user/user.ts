import { defineStore } from 'pinia'

export const useUserStore = defineStore({
  id: 'user',
  state: (): UserState => ({
    sessionId: ''
  }),
  persist: true,
  getters: {
    getSessionId(): string {
      return this.sessionId
    }
  },
  actions: {
    setSessionId(sessionId: string): void {
      this.sessionId = sessionId
    }
  }
})
