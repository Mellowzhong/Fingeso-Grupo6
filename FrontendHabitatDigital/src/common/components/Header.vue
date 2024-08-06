<script setup>
import { ref } from 'vue';
import AccountAuthModal from './AccountAuthModal.vue';

const isAccountAuthModalOpen = ref(false);

const handleIsAccountAuthModalOpen = () => {
    isAccountAuthModalOpen.value = !isAccountAuthModalOpen.value;
}
</script>

<template>
    <header>
        <nav class="bg-blue-950 text-teal-400 flex justify-between px-32 py-2 space-x-32">
            <div class="flex items-center space-x-4">
                <button class="material-symbols-rounded">menu</button>
                <h1>Logo</h1>
            </div>
            <form action="" class="flex w-full">
                <input type="text" placeholder="Buscar un inmueble" class="w-full px-3 py-2 rounded-l-xl">
                <button class="bg-teal-400 text-blue-950 grid px-3 py-2 rounded-r-xl">
                    <span class="material-symbols-rounded">search</span>
                </button>
            </form>
            <div class="flex items-center space-x-4">
                <button class="material-symbols-rounded">notifications</button>
                <button @click="handleIsAccountAuthModalOpen" class="material-symbols-rounded">account_circle</button>
            </div>
        </nav>
        <AccountAuthModal v-model:isOpen="isAccountAuthModalOpen" v-model:onClose="handleIsAccountAuthModalOpen"/>
    </header>
</template>