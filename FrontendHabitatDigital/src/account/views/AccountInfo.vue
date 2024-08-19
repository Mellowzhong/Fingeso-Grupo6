<script setup>
import { useStore } from 'vuex';
import { computed, onMounted, ref } from 'vue';
import { updateUsuario } from '../services/UserServices';
import { getAllPropertysByOwner } from '../../property/services/PropertyServices';

const store = useStore();
const user = computed(() => store.getters.getUsuario);
const showImageInput = ref(false);

const updateUser = async () => {
    console.log(user.value)
    const response = await updateUsuario(user.value);
    if (response.success == true) {
        //Actualizar el usuario en el store
        const newUser = {
            ...user.value,
            profile: {
                ...response.data
            }
        }
        console.log(response.data, 'Usuario update')
        store.dispatch('clearUsuario');
        store.dispatch('setUsuario', newUser);
    } else {
        console.log('Error al actualizar usuario')
    }
}

const getAllPropertysByUser = async () => {
    const response = await getAllPropertysByOwner(user.value.id);
    if (response.success == true) {
        console.log(response.data, 'Propiedades del usuario')
    } else {
        console.log('Error al obtener propiedades del usuario')
    }
}

onMounted(() => {
    getAllPropertysByUser();
})
</script>

<template>
    <div class="flex flex-col flex-grow h-screen bg-gray-200 rounded-xl mx-32 my-4 p-4">
        <section class="flex items-center">
            <section class="grid text-center">
                <img v-if="!showImageInput" class="rounded-full aspect-square object-cover" :src="user.profile.photo"
                    width="350" height="350" alt="Foto de perfil" />
                <!-- Add photo input -->
                <div v-if="showImageInput" class="w-full px-3 m-auto">
                    <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" for="imageUrl">
                        URL de la imagen:
                    </label>
                    <div class="flex">
                        <input v-model="user.profile.photo" type="text" id="imageUrl" name="imageUrl"
                            placeholder="https://ejemplo.com/imagen.jpg"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded-l py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                        <button @click="updateUser"
                            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-r">
                            Agregar
                        </button>
                    </div>
                </div>
                <!-- Add photo button -->
                <div v-if="user.profile.photo === null" class="mt-4">
                    <button @click="showImageInput = !showImageInput"
                        class="bg-blue-500 hover:bg-blue-700 disabled:bg-red-500 text-white font-bold py-2 rounded focus:outline-none focus:shadow-outline w-1/2">
                        {{ showImageInput ? 'Cancelar' : 'Agregar imagen' }}
                    </button>
                </div>
            </section>
            <div class="grid text-center mx-auto space-y-2">
                <h2 class="text-8xl">{{ user.profile.firstname }} {{ user.profile.lastname }}</h2>
                <div class="flex justify-center items-center space-x-2">
                    <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                        {{ user.role }}
                    </div>
                    <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                        <span class="material-symbols-rounded">hotel_class</span>
                        5
                    </div>
                </div>
            </div>
        </section>
        <section class="mt-8">
            <!-- <div v-if="user.profile.description === null"> -->
            <div>
                <h2 class="text-2xl">Descripción</h2>
                <label for="Description">
                    <textarea v-model="user.profile.description" id="description" name="description"
                        placeholder="Descripción del usuario" class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 
                    rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                </textarea>
                </label>
                <button @click="updateUser"
                    class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-r mt-4">
                    Agregar
                </button>
            </div>
            {{ user.profile.description }}
        </section>
        <section v-if="user.role === 'OWNER'" class="mt-8">
            <h2 class="text-2xl">Inmuebles:</h2>
            <div>

            </div>
        </section>
    </div>
</template>
