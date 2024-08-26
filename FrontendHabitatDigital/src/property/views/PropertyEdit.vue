<script setup>
import PropertyEditForm from '../components/PropertyEditForm.vue';
import { ref, computed } from 'vue';
import { postProperty } from '../services/PropertyServices';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const router = useRouter();
const store = useStore();

const property = computed(() => store.getters.getProperty);

const saveProperty = async () => {
    const response = await postProperty(property.value);

    if (response.success) {
        //Actualizar el usuario en el store
        router.push('/');
        console.log('save user same time as property');
    } else {
        console.log('error saving property');
    }
};

</script>

<template>
    <div>
        <PropertyEditForm :property="property" @save="saveProperty" :isEdit="false" />
    </div>
</template>