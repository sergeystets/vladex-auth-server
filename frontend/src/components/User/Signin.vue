<template>
  <v-container>
    <v-layout row>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-text>
            <h1 class="mb-2 di 1 text-left">{{ $t('label.sign-in') }}</h1>
            <v-container>
              <form @submit.prevent="onPhoneEntered">
                <v-layout row>
                  <v-flex xs12>
                    <v-text-field
                        name="phone"
                        :label="$t('input.hint.phoneNumber')"
                        id="phone"
                        v-model="phone"
                        type="text"
                        required></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout>
                  <v-flex xs12>
                    <v-btn type="submit" :loading="verifyPhoneLoading">{{ $t('button.next') }}</v-btn>
                  </v-flex>
                </v-layout>
              </form>
            </v-container>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      phone: '',
    }
  },
  mounted() {
    this.phone = this.$store.getters.phone;
  },
  computed: {
    verifyPhoneLoading() {
      return this.$store.getters.verifyPhoneLoading
    }
  },
  watch: {
    verifyPhoneLoading(value) {
      if (value === false) {
        this.$router.push('/sign-in/otp')
      }
    }
  },
  methods: {
    onPhoneEntered() {
      this.$store.dispatch('signIn', {phone: this.phone})
    },
  }
}
</script>
