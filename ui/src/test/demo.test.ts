import { expect, test } from 'vitest'
import utils from '@/utils'
test('ceshi', () => {
  const modules = import.meta.glob('/src/views/**/**.tsx')
  let m = utils.mapping(modules)
  console.log(m)
})
