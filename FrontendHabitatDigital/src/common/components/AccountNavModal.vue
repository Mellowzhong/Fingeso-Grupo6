<script setup>
    import { defineProps } from 'vue';
    import { useStore } from 'vuex';
    import { RouterLink, useRouter } from 'vue-router';

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

    const store = useStore();

    const router = useRouter();

    const logout = () => {
        document.cookie = "access_token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        router.push('/');
        store.dispatch('logoutUser');

    };

    
</script>

<template>
    <aside v-show="isOpen" @click="onClose" class="bg-black bg-opacity-50 fixed inset-0 z-50">
        <nav class="bg-white w-40 absolute top-16 right-32 rounded-xl p-4">
            <ul class="flex flex-col items-center space-y-2">
                <li><RouterLink to="/user">Ir a mi perfil</RouterLink></li>
                <li><RouterLink to="/propertyRegister">Publicar Inmueble</RouterLink></li>
                <li>Configuración</li>
                <li @click="logout">Cerrar sesión</li>
            </ul>
        </nav>
    </aside>
</template>