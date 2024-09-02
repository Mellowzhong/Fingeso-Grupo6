<script setup>
import { computed, ref } from 'vue';

const props = defineProps({
    property: {
        type: Object,
        required: true
    },
    isEdit: {
        type: Boolean,
        default: false
    }
});

const showCorredores = ref(false);

// Computed property to check if the form is empty (disable the submit button if true)
const propertyFormIsEmpty = computed(() => {
    return (
        props.property.price === '' ||
        props.property.address === '' ||
        props.property.type === '' ||
        props.property.rooms === '' ||
        props.property.bathrooms === '' ||
        props.property.squareMeters === '' ||
        props.property.yearConstruction === '' ||
        props.property.state === '' ||
        props.property.description === '' ||
        props.property.services === ''
    );
});

const newImageUrl = ref('');

const addImage = () => {
    if (newImageUrl.value && !props.property.photos.includes(newImageUrl.value)) {
        props.property.photos.push(newImageUrl.value);
        newImageUrl.value = '';
    }
};

const removeImage = (index) => {
    props.property.photos.splice(index, 1);
};

const emits = defineEmits(['save']);

const saveForm = () => {
    emits('save');
};
</script>

<template>
    <div class="flex justify-center py-12">
        <div class="w-full max-w-xs">

            <!-- Title for the property form -->
            <h1 class="flex justify-center text-gray-700 text-sm font-bold mb-2">
                {{ isEdit ? 'Editar Propiedad' : 'Registrar Propiedad' }}
            </h1>

            <!-- Property registration form -->
            <form class="w-full max-w-lg bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4" @submit.prevent="saveForm">
                <!-- Form fields for property details -->
                <div class="flex flex-wrap -mx-3 mb-6 justify-center">
                    <!-- Sale input field -->
                    <div class="w-full px-3 mb-6 md:mb-0"> 
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"> 
                            Tipo de propiedad: 
                        </label> 
                        <div class="mt-2"> 
                            <label class="inline-flex items-center"> 
                                <input type="radio" class="form-radio" name="propertyType" :value="true" v-model="property.sale"> 
                                <span class="ml-2">Venta</span> 
                            </label> 
                            <label class="inline-flex items-center ml-6"> 
                                <input type="radio" class="form-radio" name="propertyType" :value="false" v-model="property.sale"> 
                                <span class="ml-2">Arriendo</span>
                            </label> 
                        </div> 
                    </div>

                    <!-- Price input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" for="price">
                            Precio:
                        </label>
                        <input v-model="property.price" type="number" id="price" name="price" placeholder="100000"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                    </div>

                    <!-- Address input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="address">
                            Dirección:
                        </label>
                        <input v-model="property.address" type="text" id="address" name="address"
                            placeholder="Calle Falsa 123"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                    </div>

                    <!-- Type input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" for="type">
                            Tipo:
                        </label>
                        <select v-model="property.type" id="type" name="type"
                            placeholder="Apartamento"
                            class="block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                            <option value="Apartamento">Apartamento</option>
                            <option value="Casa">Casa</option>
                        </select>
                    </div>

                    <!-- Room Number input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="roomNumber">
                            Número de habitaciones:
                        </label>
                        <input v-model="property.rooms" type="number" id="roomNumber" name="roomNumber" placeholder="3"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                    </div>

                    <!-- Toilet Number input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="toiletNumber">
                            Número de baños:
                        </label>
                        <input v-model="property.bathrooms" type="number" id="toiletNumber" name="toiletNumber"
                            placeholder="2"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                    </div>

                    <!-- Square Meters input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="squareMeters">
                            Metros cuadrados:
                        </label>
                        <input v-model="property.squareMeters" type="number" id="squareMeters" name="squareMeters"
                            placeholder="120"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                    </div>

                    <!-- Build Year input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="buildYear">
                            Año de construcción:
                        </label>
                        <input v-model="property.yearConstruction" type="number" id="buildYear" name="buildYear"
                            placeholder="2000"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                    </div>

                    <!-- Property State input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="propertyState">
                            Estado de la propiedad:
                        </label>
                        <input v-model="property.state" type="text" id="propertyState" name="propertyState"
                            placeholder="Bueno"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                    </div>

                    <!-- Description input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="description">
                            Descripción:
                        </label>
                        <textarea v-model="property.description" id="description" name="description"
                            placeholder="Descripción de la propiedad"
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"></textarea>
                    </div>

                    <!-- Services Around input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="servicesArround">
                            Servicios cercanos:
                        </label>
                        <input v-model="property.services" type="text" id="servicesArround" name="servicesArround"
                            placeholder="Supermercado, colegio, etc."
                            class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                    </div>

                    <!-- Image URL input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="imageUrl">
                            URL de la imagen:
                        </label>
                        <div class="flex">
                            <input v-model="newImageUrl" type="text" id="imageUrl" name="imageUrl"
                                placeholder="https://ejemplo.com/imagen.jpg"
                                class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded-l py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                            <button @click.prevent="addImage"
                                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-r">
                                Añadir
                            </button>
                        </div>
                    </div>

                    <!-- Display added images -->
                    <div v-if="property.photos.length > 0" class="w-full px-3 mt-4">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                            Imágenes añadidas:
                        </label>
                        <ul class="list-disc pl-5">
                            <li v-for="(image, index) in property.photos" :key="index" class="mb-2">
                                {{ image }}
                                <button @click.prevent="removeImage(index)"
                                    class="ml-2 text-red-500 hover:text-red-700">
                                    Eliminar
                                </button>
                            </li>
                        </ul>
                    </div>

                    <!-- Parking input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" for="parking">
                            Estacionamiento:
                        </label>
                        <input v-model="property.parking" type="checkbox" id="parking" name="parking"
                            class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                    </div>

                    <!-- Furnished input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="furnished">
                            Amoblado:
                        </label>
                        <input v-model="property.furnished" type="checkbox" id="furnished" name="furnished"
                            class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                    </div>

                    <!-- Aprobated input field -->
                    <div class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="corredor">
                            Corredor:
                        </label>
                        <input v-model="showCorredores" type="checkbox" id="corredor" name="corredor"
                            class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                    </div>

                    <!-- List of Corredores -->
                    <div v-if="showCorredores" class="w-full px-3">
                        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                            for="corredorSelect">
                            Seleccionar Corredor:
                            <select v-model="property.corredor" id="corredorSelect" name="corredorSelect"
                                placeholder="Seleccione un corredor"
                                class="block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                                <option value="">Seleccione un corredor</option>
                                <option value="Corredor 1">Corredor 1</option>
                                <option value="Corredor 2">Corredor 2</option>
                                <option value="Corredor 3">Corredor 3</option>
                            </select>
                        </label>
                    </div>
                </div>

                <!-- Submit button to save the property -->
                <div class="flex justify-center">
                    <button type="submit" :disabled="propertyFormIsEmpty"
                        class="bg-blue-500 hover:bg-blue-700 disabled:bg-red-500 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                        {{ isEdit ? 'Guardar cambios' : 'Crear Propiedad' }}
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>