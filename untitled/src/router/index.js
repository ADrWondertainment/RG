import { createRouter, createWebHashHistory } from 'vue-router'

import Main from '~/components/Main.vue'
import Login from '~/components/Login.vue'
import test from '~/components/card.vue'

import HelloWorld from '~/components/HelloWorld.vue'
import CreateForm from '~/components/Pages/CreatForm.vue'
import FillForm from '~/components/Pages/FillForm.vue'
// import CreateInput from '~/components/CreateForm/CreateInput.vue'
import SignIn from '~/components/SignIn.vue'

const routes = [
    {
        path: '/',
        component: Main,
        children: [
            {
                path: '/userInfo',
                name: 'UserInfo',
                component: HelloWorld,
            },
            {
                path: '/createForm',
                name: 'CreateForm',
                component: CreateForm,
            },
            // {
            //     path: '/createInput',
            //     name: 'CreateInput',
            //     component: CreateInput,
            // },
            {
                path: '/fillForm',
                name: 'FillForm',
                component: FillForm,
            },
        ]
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/signIn',
        component: SignIn,
    },
    {
        path: '/test',
        component: test
    },

]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

export default router