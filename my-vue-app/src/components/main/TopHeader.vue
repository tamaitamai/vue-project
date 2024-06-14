<script setup></script>
<template>
    <header>
        <RouterLink to="/" class="home-box">ホーム</RouterLink>
        <div class="login-box">
            <RouterLink to="/insert">新規登録</RouterLink>
            <RouterLink to="/login">ログイン</RouterLink>
        </div>
    </header>
    
</template>
<style scoped>
header{
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: bisque;
    border-radius: 10px;
    box-shadow: 2px 2px 7px rgba(0, 0, 0, 0.5);
}

.home-box{
    margin: 20px;
}

.login-box{
    display: flex;
    flex-direction: column;
    margin: 20px;
}
</style>