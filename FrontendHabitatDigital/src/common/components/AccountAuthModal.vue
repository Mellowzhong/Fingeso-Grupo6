<script setup>
import { ref, defineProps } from 'vue';
import LoginForm from '../../account/components/LoginForm.vue';
import SignUpForm from '../../account/components/SignUpForm.vue';

const props = defineProps({
    isOpen: {
        type: Boolean,
        required: true,
    },
    onClose: {
        type: Function,
        required: true,
    }
});

const isLoginForm = ref(true);

const handleFormChange = () => {
    isLoginForm.value = !isLoginForm.value;
}
</script>

<template>
    <aside v-show="isOpen" @click="onClose"
        class="bg-black bg-opacity-50 fixed inset-0 z-50 flex justify-center items-center">
        <section @click.stop class="bg-white w-96 p-4 rounded-2xl">
            <header class="relative flex justify-center">
                <button v-show="!isLoginForm" @click="handleFormChange" class="absolute left-0 grid">
                    <span class="material-symbols-rounded">arrow_back</span>
                </button>
                <h2>{{ isLoginForm ? "Iniciar sesión" : "Registrarse" }}</h2>
                <button @click="onClose" class="absolute right-0 grid">
                    <span class="material-symbols-rounded">close</span>
                </button>
            </header>

            <body>
                <LoginForm v-if="isLoginForm" v-model:onClose="props.onClose" />
                <SignUpForm v-else v-model:onClose="props.onClose" v-model:changeToLoginForm="handleFormChange" />
            </body>
            <footer v-show="isLoginForm" class="flex flex-col pt-4">
                <p class="text-xs text-center mb-1">¿todavia no tienes una cuenta?</p>
                <button @click="handleFormChange" class="bg-teal-400 p-1 rounded">Registrate</button>
            </footer>
        </section>
    </aside>
</template>