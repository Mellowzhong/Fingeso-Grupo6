<script setup>
import { ref, computed } from 'vue';
import AccountAuthModal from './AccountAuthModal.vue';
import AccountNavModal from './AccountNavModal.vue';
import { useStore } from 'vuex';
import { RouterLink } from 'vue-router';

const isAccountAuthModalOpen = ref(false);
const store = useStore();
const usuario = computed(() => store.getters.getUsuario);

const handleIsAccountAuthModalOpen = () => {
    isAccountAuthModalOpen.value = !isAccountAuthModalOpen.value;
}

</script>

<template>
    <header>
        <nav class="bg-blue-950 text-teal-400 flex justify-between px-32 py-2">
            <div class="flex items-center space-x-4">
                <button class="material-symbols-rounded">menu</button>
                <RouterLink to="/">
                    <h1>HD</h1>
                </RouterLink>
                <form action="" class="flex w-96 pl-10">
                    <input type=" text" placeholder="Buscar un inmueble" class="w-full px-3 py-2 rounded-l-xl">
                    <button class="bg-teal-400 text-blue-950 grid px-3 py-2 rounded-r-xl">
                        <span class="material-symbols-rounded">search</span>
                    </button>
                </form>
            </div>
            <div class="flex items-center space-x-4">
                <button class="material-symbols-rounded">notifications</button>
                <button @click="handleIsAccountAuthModalOpen" class="material-symbols-rounded">account_circle</button>
                <RouterLink to="/user">
                    <span v-if="usuario"> {{ usuario.profile.firstname }} </span>
                </RouterLink>

            </div>
        </nav>
        <AccountAuthModal v-if="!usuario" v-model:isOpen="isAccountAuthModalOpen"
            v-model:onClose="handleIsAccountAuthModalOpen" />
        <!-- <AccountNavModal v-else /> -->
    </header>
</template>