import { createApp } from "vue";
import App from "./App.vue";

// import "~/styles/element/index.scss";

import ElementPlus from "element-plus";
// import all element css, uncommented next line
import "element-plus/dist/index.css";

import "~/styles/index.scss";

// If you want to use ElMessage, import it.
import "element-plus/theme-chalk/src/message.scss"

// Icons 
import * as ElIconModules from '@element-plus/icons-vue'

import router from '~/router'

const app = createApp(App);
for (const iconName in ElIconModules) {
    if (Reflect.has(ElIconModules, iconName)) {
        const item = ElIconModules[iconName]
        app.component(iconName, item)
    }
}
app.use(router)

// app.use(ElementPlus);
const vm = app.mount("#app");

vm.$router.push('/login')