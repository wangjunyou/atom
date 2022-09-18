import {Component} from 'vue'

/* 路由路径默认映射工具类 */
const mapping = (modules: any) => {
    const components: { [key: string]: Component } = {}
    Object.keys(modules).forEach((key: string) => {
        const nameMatch: string[] | null = key.match(/^\/src\/views\/(.+)\.tsx/)

        if (!nameMatch) {
            return
        }

        const indexMatch: string[] | null = nameMatch[1].match(/(.*)\/Index$/i)

        let name: string = indexMatch ? indexMatch[1] : nameMatch[1]

        name = name.replaceAll('/', '-')

        components[name] = modules[key]
    })
    return components
}

export default mapping