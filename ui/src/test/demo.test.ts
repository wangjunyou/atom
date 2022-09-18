import {expect, test} from 'vitest'
import {Component} from "vue";
import sum from '../views/demo'

const mapping = (modules: any) => {
    const components: { [key: string]: Component } = {}
    // All TSX files under the views folder automatically generate mapping relationship
    Object.keys(modules).forEach((key: string) => {
        const nameMatch: string[] | null = key.match(/^\/src\/views\/(.+)\.tsx/)

        if (!nameMatch) {
            return
        }

        // If the page is named Index, the parent folder is used as the name
        const indexMatch: string[] | null = nameMatch[1].match(/(.*)\/Index$/i)

        let name: string = indexMatch ? indexMatch[1] : nameMatch[1]

        name = name.replaceAll('/', '-')

        components[name] = modules[key]
    })
    return components
}

test('ceshi',() => {
    const modules = import.meta.glob('/src/views/**/**.tsx')
    console.log(modules)
    Object.keys(modules).forEach((key: string) =>{
        console.log(key)
    })
    let m = mapping(modules)
    console.log(m)
    const a: string[] | null = [];
    console.log(a[1])
    expect(sum(1,2)).toBe(3)
})