import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'

import Main from '~/components/Main.vue'
import Login from '~/components/Pages/Login.vue'
import test from '~/components/card.vue'

import PersonnelManagement from "~/components/Pages/PersonnelManagement.vue"
import DetailPM from "~/components/Pages/DetailPM.vue";
// import CreateInput from '~/components/CreateForm/CreateInput.vue'

import UserPage from '~/components/Pages/UserPage.vue'
import CreateForm from '~/components/Pages/CreateForm.vue'
import BeforeMain from '~/components/Pages/BeforeMain.vue'
import FillForm from '~/components/Pages/FillForm.vue'
import JustFillForm from '~/components/Pages/JustFillForm.vue'
import FormApprove from '~/components/Pages/AllFormsToApprove.vue'
import ShowFormApprove from '~/components/Pages/ShowFormApprove.vue'
import SignIn from '~/components/Pages/SignIn.vue'
import SignInC from '~/components/Pages/SignInC.vue'
import FormTemplates from '~/components/Pages/ManageFormTemplates.vue'
import AllPublicForms from '~/components/Pages/AllPublicForms.vue'
import AllFormsIFilledOut from '~/components/Pages/AllFormsIFilledOut.vue'
import ResultOfOneForm from '~/components/Pages/ResultOfOneForm.vue'
import OrganizationStructure from '~/components/Pages/OrganizationStructure.vue'

const routes = [
    {
        path: '/',
        component: Main,
        children: [
            {
                path: '/userInfo',
                name: 'UserInfo',
                component: UserPage,
            },
            {
                path: '/createForm',
                name: 'CreateForm',
                component: CreateForm,
            },
            {
                path:'/organizationStructure',
                name:'OrganizationStructure',
                component: OrganizationStructure,
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
            {
                path: '/manageFormTemplates',
                name: 'ManageFormTemplates',
                component: FormTemplates,
            },
            {
                path: '/formApprove',
                name: 'FormApprove',
                component: FormApprove,
            },
            {
                path: 'showFormApprove',
                name: 'ShowFormApprove',
                component: ShowFormApprove,
            },
            {
                path:'/PersonnelManagement',
                name:'PersonnelManagement',
                component: PersonnelManagement,
                children:[

                ]

            },
            {
                path:'/DetailPM',
                name:'DetailPM',
                component:DetailPM
            },
            {
                path:'/allPublicForms',
                name:'AllPublicForms',
                component:AllPublicForms
            },
            {
                path:'/resultOfOneForm',
                name:'ResultOfOneForm',
                component:ResultOfOneForm
            },
            {
                path:'/allFormsIFilledOut',
                name:'AllFormsIFilledOut',
                component:AllFormsIFilledOut
            }
        ]
    },
    {
        path: '/beforeMain',
        name: 'BeforeMain',
        component: BeforeMain,
        children:[
            {
                path: '/login',
                name: 'Login',
                component: Login,
                meta: {
                    id: 1
                }
            },
            {
                path: '/signIn',
                component: SignIn,
            },
            {
                path: '/signInC',
                component: SignInC,
            },
        ]
    },
    {
        path: '/test',
        component: test
    },
    {
        path: '/justFillForm/:FormId',
        component: JustFillForm
    },

]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

router.beforeEach((to, from) => {
    // console.log(to.path)
    // 已登录 || 要跳转到login || 要到signIn
    if (sessionStorage["isLogin"] || to.path === '/login' || to.path === '/signIn' || to.path === '/signInC') {
        return true
    }
    // else if (sessionStorage["isLogin"] && to.path != '/Login') {
    //     return true
    // }
    else {
        sessionStorage["targetPage"] = to.path;
        return { name: 'Login' }
    }
    
})
export default router