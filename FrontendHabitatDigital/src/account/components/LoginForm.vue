<script setup>
import { ref } from 'vue';

const email = ref('');
const password = ref('');
</script>

<template>
    <form action="" class="flex flex-col border-b-2 space-y-4 py-4">
        <label for="">
            <p class="text-sm ml-2 mb-1">Correo Electrónico:</p>
            <div class="flex">
                <input type="email" placeholder="Ingrese su correo" required class="w-full border rounded-l-lg pl-2 py-1">
                <span class="material-symbols-rounded border rounded-r-lg p-1">mail</span>
            </div>
        </label>
        <label for="">
            <p class="text-sm ml-2 mb-1">Contraseña:</p>
            <div class="flex">
                <input type="password" placeholder="Ingrese su contraseña" required class="w-full border rounded-l-lg pl-2 py-1">
                <span class="material-symbols-rounded border rounded-r-lg p-1">lock</span>
            </div>
        </label>
        <button class="bg-teal-400 p-1 rounded">Iniciar sesion</button>
    </form>
</template>