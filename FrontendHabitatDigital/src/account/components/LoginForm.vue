<script setup>
import { ref } from 'vue';
import { getUser } from '../services/UserServices';

const props = defineProps({
    onClose: {
        type: Function,
        required: true,
    }
});

const email = ref('');
const password = ref('');

const showPassword = ref(false);

const handleShowPassword = () => {
    showPassword.value = !showPassword.value;
}

const login = async () => {
    console.log("login", email.value, password.value);
    const response = await getUser(email.value, password.value);
    if (response.success == true) {
        console.log('Usuario logueado con éxito')
        props.onClose();
    } else {
        console.log('Error al loguear usuario')
    }
}
</script>

<template>
    <form action="" class="flex flex-col border-b-2 space-y-4 py-4" v-on:submit.prevent="login">
        <label for="">
            <p class="text-sm ml-2 mb-1">Correo Electrónico:</p>
            <div class="flex">
                <input type="email" placeholder="Ingrese su correo" required
                    class="w-full border rounded-l-lg pl-2 py-1" v-model="email">
                <span class="material-symbols-rounded border rounded-r-lg p-1">mail</span>
            </div>
        </label>
        <label for="">
            <p class="text-sm ml-2 mb-1">Contraseña:</p>
            <div class="flex">
                <input v-if="showPassword" type="text" placeholder="Ingrese su contraseña" required
                    class="w-full border rounded-l-lg pl-2 py-1" v-model="password">
                <input v-else type="password" placeholder="Ingrese su contraseña" required
                    class="w-full border rounded-l-lg pl-2 py-1" v-model="password">

                <span class="material-symbols-rounded border rounded-r-lg p-1">lock</span>
            </div>
        </label>
        <label for="" class="flex items-center space-x-2 pl-1">
            <p class="text-sm">Mostrar contraseña</p>
            <input type="checkbox" @click="handleShowPassword">
        </label>
        <button class="bg-teal-400 p-1 rounded">Iniciar sesion</button>
    </form>
</template>