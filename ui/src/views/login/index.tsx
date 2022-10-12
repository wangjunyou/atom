import {defineComponent} from 'vue'
import styles from './index.module.scss'
import {NForm, NFormItem, NInput} from "naive-ui";

const login = defineComponent({
    setup() {
    },
    render() {
        return (
            <div class={styles.login}>
                <div class={styles['login-model']}>
                    <NForm>
                        <NFormItem
                            label='feijichang'
                            path='userName'
                        >
                            <NInput/>
                        </NFormItem>
                    </NForm>
                </div>
            </div>
        )
    }
})

export default login
