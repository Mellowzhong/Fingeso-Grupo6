<script setup>
import { ref } from 'vue';
import { register } from '../services/UserServices';
import { useCookies } from 'vue3-cookies';
import { ACCESS_TOKEN } from '../../utilities/constants/constants';

const props = defineProps({
    onClose: {
        type: Function,
        required: true,
    },
    changeToLoginForm: {
        type: Function,
        required: true,
    }
});

const { cookies } = useCookies();

const user = ref({
    username: '',
    password: '',
    firstname: '',
    lastname: '',
    contact: '',
})

const roles = [
    { name: 'Usuario', value: 'user' },
    { name: 'Propietario', value: 'owner' },
    { name: 'C. de I.', value: 'property broker' },
]

const clearUser = () => {
    user.value = {
        username: '',
        password: '',
        firstname: '',
        lastname: '',
        contact: '',
    }
}

const signUp = async () => {
    const user_copy = { ...user.value }
    user_copy.contact = '+56' + user_copy.contact
    console.log(user_copy)
    const response = await register(user_copy);
    if (response.success == true) {
        console.log(response.data);
        cookies.set(ACCESS_TOKEN, response.data.token, { expires: '1d' });
        console.log('Usuario creado con éxito');
        clearUser();
        props.onClose();
        props.changeToLoginForm();
    } else {
        console.log('Error al crear usuario');
    }
}

</script>

<template>
    <form action="" class="flex flex-col space-y-4 pt-4" v-on:submit.prevent="signUp">
        <label for="">
            <p class="text-sm ml-2 mb-1">Correo Electrónico:</p>
            <input type="email" name="email" placeholder="Ingrese su correo" required
                class="w-full border rounded-lg px-2 py-1" v-model="user.username">
        </label>
        <label for="">
            <p class="text-sm ml-2 mb-1">Contraseña:</p>
            <input type="password" placeholder="Ingrese su contraseña" required
                class="w-full border rounded-lg px-2 py-1" v-model="user.password">
        </label>
        <div class="flex gap-4">
            <label for="">
                <p class="text-sm ml-2 mb-1">Nombre:</p>
                <input type="text" placeholder="Ingrese su nombre" required class="w-full border rounded-lg px-2 py-1"
                    v-model="user.firstname">
            </label>
            <label for="">
                <p class="text-sm ml-2 mb-1">Apellido:</p>
                <input type="text" placeholder="Ingrese su apellido" required class="w-full border rounded-lg px-2 py-1"
                    v-model="user.lastname">
            </label>
        </div>
        <label for="">
            <p class="text-sm ml-2 mb-1">Numero de telefono:</p>
            <div class="flex">
                <span class="bg-teal-200 rounded-l-lg px-2 py-1">+56</span>
                <input type="tel" name="" id="" pattern="[0-9]{9}" placeholder="Ingrese su número de teléfono" required
                    class="w-full border rounded-r-lg pl-2 py-1" v-model="user.contact">
            </div>
        </label>
        <!-- <label for="">
            <p class="text-sm ml-2 mb-1">Rol:</p>
            <div class="flex justify-between px-2">
                <div v-for="role in roles" class="">
                    <input type="radio" :key="role.value" name="role" :value="role.value">
                    {{ role.name }}
                </div>
            </div>
        </label> -->
        <button class="bg-teal-400 p-1 rounded">Registrarse</button>
    </form>
</template>