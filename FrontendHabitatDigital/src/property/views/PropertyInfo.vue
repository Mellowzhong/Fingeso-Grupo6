<script setup>
import { useStore } from 'vuex';
import { computed, onMounted, ref } from 'vue';
import { getOwnerProfile } from '../services/PropertyServices';

const store = useStore();

const property = computed(() => store.getters.getProperty);
const profileOwner = ref({});

const currentImageIndex = ref(0);

// Function to go to the previous image
const previousImage = () => {
    if (currentImageIndex.value > 0) {
        currentImageIndex.value--;
    } else {
        currentImageIndex.value = property.value.photos.length - 1;
    }
};

// Function to go to the next image
const nextImage = () => {
    if (currentImageIndex.value < property.value.photos.length - 1) {
        currentImageIndex.value++;
    } else {
        currentImageIndex.value = 0;
    }
};

const getFirstName = async (inmuebleId) => {
    const response = await getOwnerProfile(inmuebleId);
    if (response.success) {
        profileOwner.value = response.data;
    } else {
        console.log('Error al obtener el nombre del propietario');
    }
};

onMounted(async () => {
    await getFirstName(property.value.id);
})
</script>

<template>
    <div class="flex flex-col flex-grow h-screen bg-gray-200 rounded-xl mx-32 my-4 p-4">
        <div class="flex flex-grow">
            <figure class="bg-gray-400 w-[40rem] h-[32rem] flex-1 overflow-hidden relative">
                <!-- Imágenes -->
                <div v-if="property.photos.length === 0">
                    <img src="../../Images/noImage.png" alt="Imagen de la propiedad"
                        class="absolute top-0 left-0 w-full h-full object-cover transition-opacity duration-300">
                </div>
                <div v-else>
                    <img v-for="(image, index) in property.photos" :key="index" :src="image"
                        :alt="`Imagen ${index + 1} de la propiedad`"
                        class="absolute top-0 left-0 w-full h-full object-cover transition-opacity duration-300"
                        :class="{ 'opacity-100': index === currentImageIndex, 'opacity-0': index !== currentImageIndex }">

                    <!-- Flecha izquierda -->
                    <button v-if="property.photos.length > 1" @click="previousImage"
                        class="absolute left-0 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-2 rounded-r">
                        &#10094;
                    </button>

                    <!-- Flecha derecha -->
                    <button v-if="property.photos.length > 1" @click="nextImage"
                        class="absolute right-0 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-2 rounded-l">
                        &#10095;
                    </button>
                </div>
            </figure>
            <figcaption class="flex-1 p-4 space-y-2">
                <h1 class="text-4xl">{{ property.type }}</h1>
                <section class="space-y-2">
                    <div class="flex space-x-2">
                        <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            <span class="material-symbols-rounded">
                                bed
                            </span>
                            {{ property.rooms }}
                        </div>
                        <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            Baños: {{ property.bathrooms }}
                        </div>
                        <div v-show="property.parking"
                            class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            <span class="material-symbols-rounded">
                                directions_car
                            </span>
                        </div>
                        <div v-show="property.furnished"
                            class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            Amoblado
                        </div>
                        <div class="bg-black bg-opacity-20 flex items-center rounded-xl px-3 py-0.5">
                            {{ property.squareMeters }} Mt²
                        </div>
                    </div>
                    <div>
                        <h2 class="text-2xl">Servicios Cercanos:</h2>
                        <p>{{ property.services }}</p>
                    </div>
                </section>
                <section>
                    <h2 class="text-2xl">Dirección:</h2>
                    <p>{{ property.address }}</p>
                </section>
                <section>
                    <h2 class="text-2xl">Precio:</h2>
                    <p>UF {{ property.price }}</p>
                </section>
                <div class="flex">
                    <section class="flex-1">
                        <h2 class="text-2xl">Año de construcción:</h2>
                        <p>{{ property.yearConstruction }}</p>
                    </section>
                    <section class="flex-1">
                        <h2 class="text-2xl">Estado:</h2>
                        <p>{{ property.state }}</p>
                    </section>
                </div>
                <section>
                    <h2 class="text-2xl">Propietario / Corredor de inmuebles</h2>
                    <div class="flex items-center space-x-2">
                        <img class="rounded-full aspect-square object-cover" :src="profileOwner.photo" width="64"
                            height="64" alt="Foto de perfil" />
                        <h3>{{ profileOwner.firstname }} {{ profileOwner.lastname }}</h3>
                    </div>
                </section>
                <section>
                    <h2 class="text-2xl">Descripción</h2>
                    <p>{{ property.description }}</p>
                </section>
            </figcaption>
        </div>
        <div class="flex items-center space-x-2 mt-auto">
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar Compra</button>
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar Arriendo</button>
            <button class="bg-blue-500 text-white rounded-lg p-2">Solicitar agendamiento</button>
        </div>
    </div>
</template>