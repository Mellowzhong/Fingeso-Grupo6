<script setup>
import PropertyForm from '../components/PropertyForm.vue';
import { ref, computed } from 'vue';
import { postProperty } from '../services/PropertyServices';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const router = useRouter();
const store = useStore();

const user = computed(() => store.getters.getUsuario);

const property = ref({
    disponibility: false,
    sale: false,
    price: '',
    direction: '',
    type: '',
    rooms: '',
    bathrooms: '',
    squareMeters: '',
    yearConstruction: '',
    state: '',
    description: '',
    photos: [],
    services: '',
    parking: false,
    furnished: false,
    aprobated: false,
});

const saveProperty = async () => {
    const response = await postProperty(user.value.username, property.value);

    if (response.success) {
        router.push('/');
        console.log('save user same time as property');
    } else {
        console.log('error saving property');
    }
};

</script>

<template>
    <div>
        <PropertyForm :property="property" @save="saveProperty" :isEdit="false" />
    </div>
</template>