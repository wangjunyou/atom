import { defineStore } from 'pinia';
export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        sessionId: ''
    }),
    persist: true,
    getters: {
        getSessionId() {
            return this.sessionId;
        }
    },
    actions: {
        setSessionId(sessionId) {
            this.sessionId = sessionId;
        }
    }
});
//# sourceMappingURL=user.js.map