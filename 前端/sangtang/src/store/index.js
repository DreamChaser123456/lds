import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)



//全局state对象，用于保存所有组件的公共数据
const state=sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')) : {
    user:{
        
    },
    login:{
        
    }
};

// 监听state对象的值的最新状态(计算属性)
const getters={
    getUser(state){
        return state.user;
    },
    getLogin(state){
        return statusbar.login;
    }
};

//唯一一个可以修改state值得方法(同步执行的)
const mutations={
    updateUser(state, user){
        state.user = user;
    },
    updateLogin(state,login){
        state.login=login;
    }
};

//异步执行mutations方法
const actions={
    asyncUpdateUser(context, user){
        context.commit("updateUser", user);
    },
    asyncUpdateLogin(context, login){
        context.commit("updateLogin", login);
    }
};

export default new Vuex.Store({
    state,
    getters,
    mutations,
    actions,
})


