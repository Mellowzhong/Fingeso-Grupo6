<script setup>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { computed, onMounted, ref } from 'vue';
import { updateUsuario, getUsuarioByEmail } from '../services/UserServices';
import { getAllPropertysByOwner } from '../../property/services/PropertyServices';
import PropertyCard from '../../home/components/PropertyCard.vue';


const router = useRouter();
const store = useStore();
const user = computed(() => store.getters.getUsuario);
const showImageInput = ref(false);
const showDescriptionInput = ref(false);
const properties = ref([]);

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
        showDescriptionInput.value = false;
    } else {
        console.log('Error al actualizar usuario')
        showDescriptionInput.value = false;
    }
}

const getAllPropertysByUser = async () => {
    const response = await getAllPropertysByOwner(user.value.id);
    if (response.success == true) {
        console.log(response.data, 'Propiedades del usuario')
        properties.value = response.data;
    } else {
        console.log('Error al obtener propiedades del usuario')
    }
}

const fetchUser = async () => {
    const response = await getUsuarioByEmail(user.value.username);
    if (response.success == true) {
        store.dispatch('setUsuario', response.data);
    } else {
        console.log('Error al obtener usuario')
    }
}

const selectProperty = (property) => {
    store.dispatch('selectProperty', property);
    console.log(property);
    router.push('/property');
};

const editProperty = (property) => {
    store.dispatch('selectProperty', property);
    console.log(property);
    router.push('/editProperty');
};

onMounted(() => {
    fetchUser();
    if ( user.value.role === 'OWNER' ){
        getAllPropertysByUser();
    }
})
</script>

<template>
    <div class="h-full">
        <div class="flex flex-col flex-grow min-h-screen bg-gray-200 rounded-xl mx-32 my-4 p-4">
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
                    <div class="mt-4">
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
                        <div v-if="user.role === 'CORREDOR'">
                            <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                                <span class="material-symbols-rounded">hotel_class</span>
                                5
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="mt-8">
                <!-- <div v-if="user.profile.description === null"> -->
                <div>
                    <h2 class="text-2xl">Descripción</h2>
                    <div  v-if="showDescriptionInput"> 
                        <label for="Description">
                            <textarea v-model="user.profile.description" id="description" name="description"
                                placeholder="Descripción del usuario" class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 
                            rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"></textarea>
                        </label>
                        <button @click="updateUser"
                            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-r mt-4">
                            Agregar
                        </button>
                        <button @click="showDescriptionInput = !showDescriptionInput"
                            class="bg-red-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-r mt-4">
                            Cancelar
                        </button>
                    </div>
                </div>
                <div v-if="!showDescriptionInput" class="grid">
                    {{ user.profile.description }}
                    <button @click="showDescriptionInput = !showDescriptionInput"
                            class="bg-blue-500 hover:bg-blue-700 text-white font-bold w-64 py-2 px-4 rounded-r mt-4 mx-2">
                            Actualizar Descripción
                    </button>
                </div>  
            </section>
            <section v-if="user.role === 'OWNER'" class="mt-8">
                <h2 class="text-2xl">Inmuebles:</h2>
                <div class="grid grid-cols-5 gap-4">
                    <div v-for="property in properties" :key="property.id" class="flex flex-col items-center">
                        <PropertyCard :property @click="selectProperty(property)"/>
                        <button @click="editProperty(property)" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-64">
                        Editar Inmueble
                        </button>
                    </div>
                </div>
            </section>
        </div>
    </div>  
</template>